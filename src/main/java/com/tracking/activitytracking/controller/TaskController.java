package com.tracking.activitytracking.controller;

import com.tracking.activitytracking.dto.request.AppUserRequestDto;
import com.tracking.activitytracking.dto.request.TaskRequestDto;
import com.tracking.activitytracking.dto.response.DoneTaskResponse;
import com.tracking.activitytracking.dto.response.TaskDeleteDto;
import com.tracking.activitytracking.dto.response.TaskResponse;
import com.tracking.activitytracking.dto.response.TaskUpdateResponseDto;
import com.tracking.activitytracking.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/path")
public class TaskController {
    private final TaskService taskService;

    @PostMapping(path = "/create-task/{id}")
    public ResponseEntity<?> createUser(@PathVariable("id") Long id, @RequestBody @Valid TaskRequestDto taskRequestDto) {

        var response = taskService.createTask(taskRequestDto, id);

        // The same as above
//        UserRegistrationResponse response1 = userService.registerUser(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/update-task/{id}/{taskId}")
    public ResponseEntity<?> updateTask(@RequestBody TaskRequestDto taskRequestDto, @PathVariable("id")Long appUserId ,@PathVariable("taskId") Long taskId){
        TaskUpdateResponseDto editedTask = taskService.editTask(taskRequestDto,appUserId,taskId);
        return ResponseEntity.status(HttpStatus.CREATED).body(editedTask);
    }

    @DeleteMapping("/delete-task/{id}/{taskId}")
    public ResponseEntity<TaskDeleteDto> deleteTask(@PathVariable("id") Long appUserId, @PathVariable("taskId") Long taskId){
        TaskDeleteDto deleteDto = taskService.deleteTask(appUserId, taskId);
        return ResponseEntity.ok(deleteDto);
    }
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<TaskResponse>> viewAllTasksByUserId(@PathVariable("userId") Long userId) {
        List<TaskResponse>tasks = taskService.viewAllTask(userId);
        return ResponseEntity.ok(tasks);
    }
//    @PutMapping("/{taskId}/done")
//    public ResponseEntity<DoneTaskResponse> moveTaskToDone(@PathVariable Long taskId) {
//        DoneTaskResponse movedTask = taskService.moveTaskToDone(taskId);
//        return ResponseEntity.ok(movedTask);
//    }
@GetMapping("/task/{taskId}/{id}")
public ResponseEntity<TaskResponse> viewTask(@PathVariable("taskId") Long taskId, @PathVariable("id")Long appUserId) {
    TaskResponse taskResponse = taskService.viewATask(taskId, appUserId);
    return ResponseEntity.ok(taskResponse);
}

}
