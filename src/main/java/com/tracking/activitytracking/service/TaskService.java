package com.tracking.activitytracking.service;

import com.tracking.activitytracking.dto.request.TaskRequestDto;
import com.tracking.activitytracking.dto.response.DoneTaskResponse;
import com.tracking.activitytracking.dto.response.TaskDeleteDto;
import com.tracking.activitytracking.dto.response.TaskUpdateResponseDto;
import com.tracking.activitytracking.dto.response.TaskResponse;
import com.tracking.activitytracking.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    public TaskResponse createTask(TaskRequestDto taskRequestDto, Long id);
    TaskUpdateResponseDto editTask (TaskRequestDto request, Long taskId, Long appUserId);
    TaskDeleteDto deleteTask (Long taskId, Long appUserId);
    DoneTaskResponse moveTaskToDone (Long id);
    TaskResponse viewATask (TaskRequestDto request);

    TaskResponse viewATask(Long taskId, Long appUserId);

    TaskResponse moveTaskStatus (TaskStatus taskStatus, Long taskId, Long appUserId);
    List <TaskResponse> viewAllTask (Long appUserId);
    List <TaskResponse> viewAllPendingTask (TaskRequestDto request);
    List <TaskResponse> viewAllDoneTask (TaskRequestDto request);
    List <TaskResponse> viewAllInProgressTask (TaskRequestDto request);

}
