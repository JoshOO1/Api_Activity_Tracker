package com.tracking.activitytracking.service.implementation;

import com.tracking.activitytracking.dto.request.TaskRequestDto;


import com.tracking.activitytracking.dto.response.DoneTaskResponse;
import com.tracking.activitytracking.dto.response.TaskDeleteDto;
import com.tracking.activitytracking.dto.response.TaskUpdateResponseDto;
import com.tracking.activitytracking.dto.response.TaskResponse;
import com.tracking.activitytracking.enums.TaskStatus;
import com.tracking.activitytracking.exceptions.InvalidException;
import com.tracking.activitytracking.exceptions.ResourceNotFoundException;
import com.tracking.activitytracking.model.AppUser;
import com.tracking.activitytracking.model.Task;
import com.tracking.activitytracking.repository.AppUserRepository;
import com.tracking.activitytracking.repository.TaskRepository;
import com.tracking.activitytracking.service.TaskService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Builder

@Data
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;
    @Override
    public TaskResponse createTask(TaskRequestDto taskRequestDto, Long id) {
        AppUser checkIfUserExist = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User doesnt Exist"));

        Task task = new Task().builder()
                .title(taskRequestDto.getTitle())
                .description(taskRequestDto.getDescription())
                .taskStatus(TaskStatus.PENDING)
                .appUser(checkIfUserExist)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Task savetask = taskRepository.save(task);

        return TaskResponse.builder()
                .id(savetask.getId())
                .title(savetask.getTitle())
                .description(savetask.getDescription())
                .taskStatus(savetask.getTaskStatus())
                .createdAt(savetask.getCreatedAt())
                .updatedAt(savetask.getUpdatedAt())
                .completedAt(savetask.getCompletedAt())
                .build();
    }

    @Override
    public TaskUpdateResponseDto editTask(TaskRequestDto request, Long id, Long appUserId) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);
        if (optionalAppUser.isEmpty()) {
            throw new InvalidException("You are not an authorized User");
        } else {
            AppUser appUser = optionalAppUser.get();
            //check if the task trying to be updated actually exists in my database
            Optional<Task> optionalTask = taskRepository.findById(id);
            if (optionalTask.isEmpty()) {
                throw new InvalidException("sorry! this task does not exist", HttpStatus.BAD_REQUEST);
            } else {
                Task task = optionalTask.get();
                //compare the id of the Appuser with id of the Appuser that created the task,only when they are a match should the update be allowed
                if (!task.getAppUser().getId().equals(appUser.getId())) {
                    throw new InvalidException("You are not authorized to update this task", HttpStatus.BAD_REQUEST);
                }
                //update the task if the user is authorized
                task.setTitle(request.getTitle());
                task.setDescription(request.getDescription());
                task.setTaskStatus(TaskStatus.PENDING);
                task.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                Task updatedTask = taskRepository.save(task);

                //provide the user with a response
                TaskUpdateResponseDto taskUpdateResponseDto = new TaskUpdateResponseDto();
                taskUpdateResponseDto.setId(updatedTask.getId());
                taskUpdateResponseDto.setTitle(updatedTask.getTitle());
                taskUpdateResponseDto.setDescription(updatedTask.getDescription());
                taskUpdateResponseDto.setUpdatedAt(LocalDateTime.now());
                return  taskUpdateResponseDto;
            }
        }
        }

    @Override
    public TaskDeleteDto deleteTask( Long taskId, Long appUserId) {
        Optional<AppUser>optionalAppUser = appUserRepository.findById(appUserId);
        if(optionalAppUser.isEmpty()){
            throw new InvalidException("You do not have permission to do this");
        } else {
            AppUser appUser = optionalAppUser.get();
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if(optionalTask.isEmpty()){
                throw new InvalidException(("this task is not available"));
            }else {
                Task task = optionalTask.get();
                if(!task.getAppUser().getId().equals(appUser.getId())) {
                    throw new InvalidException("This task does not belong to the user");

                }
                taskRepository.delete(task);
                TaskDeleteDto deleteDto = new TaskDeleteDto();
                deleteDto.setId(taskId);
                deleteDto.setMessage("Task has been removed");
                return deleteDto;
            }
        }
    }

    @Override
    public DoneTaskResponse moveTaskToDone(Long id) {
        return null;
    }

    @Override
    public TaskResponse viewATask(TaskRequestDto request) {
        return null;
    }

//    private Task findTaskById(Long taskId) {
//        return taskRepository.findById(taskId)
//                .orElseThrow(() -> new InvalidException("Task not found"));
//    }
//    @Override
//    public DoneTaskResponse moveTaskToDone(Long taskId) {
//        Task task = findTaskById(taskId);
//        task.setTaskStatus(TaskStatus.DONE);
//        task.setCompletedAt(LocalDateTime.now());
//        task.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
//
//        Task savedTask = taskRepository.save(task);
//
//        return DoneTaskResponse.builder()
//                .id(savedTask.getId())
//                .title(savedTask.getTitle())
//                .description(savedTask.getDescription())
//                .taskStatus(savedTask.getTaskStatus())
//                .completedAt(savedTask.getCompletedAt()).build();
//    }


    @Override
    public TaskResponse viewATask(Long taskId, Long appUserId) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(appUserId);
        if (optionalAppUser.isEmpty()) {
            throw new InvalidException("User not found");
        } else {
            AppUser appUser = optionalAppUser.get();
            Optional<Task> optionalTask = taskRepository.findByIdAndAppUser(taskId, appUser);
            if (optionalTask.isEmpty()) {
                throw new InvalidException("Task not found");
            } else {
                Task task = optionalTask.get();
                return TaskResponse.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .taskStatus(task.getTaskStatus())
                        .createdAt(task.getCreatedAt())
                        .build();
            }
        }
    }

    @Override
    public TaskResponse moveTaskStatus(TaskStatus taskStatus, Long taskId, Long appUserId) {
        return null;
    }

    @Override
    public List<TaskResponse> viewAllTask(Long appUserId) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(appUserId);
        if(optionalAppUser.isEmpty()){
            throw  new InvalidException("User not found");

        } else {
            AppUser appUser = optionalAppUser.get();
            List<Task> tasks = taskRepository.findByAppUser(appUser);

            return tasks.stream().map(task ->TaskResponse.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .description(task.getDescription())
                    .taskStatus(task.getTaskStatus())
                    .createdAt(task.getCreatedAt())
                    .build()).toList();
        }


    }



    @Override
    public List<TaskResponse> viewAllPendingTask(TaskRequestDto request) {
        return null;
    }

    @Override
    public List<TaskResponse> viewAllDoneTask(TaskRequestDto request) {
        return null;
    }

    @Override
    public List<TaskResponse> viewAllInProgressTask(TaskRequestDto request) {
        return null;
    }
}
