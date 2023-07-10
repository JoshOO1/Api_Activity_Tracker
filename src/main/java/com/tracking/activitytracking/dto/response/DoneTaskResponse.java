package com.tracking.activitytracking.dto.response;

import com.tracking.activitytracking.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DoneTaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private LocalDateTime completedAt;
}
