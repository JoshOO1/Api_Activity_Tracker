package com.tracking.activitytracking.dto.response;

import com.tracking.activitytracking.enums.TaskStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @CreatedDate
    @Column(nullable = true)
    private Timestamp createdAt;
    @LastModifiedDate
    @Column(nullable = true)
    private Timestamp updatedAt;
    @Column(nullable = true)
    private LocalDateTime completedAt;
}
