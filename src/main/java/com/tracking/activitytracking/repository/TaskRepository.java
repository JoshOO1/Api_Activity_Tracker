package com.tracking.activitytracking.repository;

import com.tracking.activitytracking.model.AppUser;
import com.tracking.activitytracking.model.Task;
import com.tracking.activitytracking.service.AppUserService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
//    Optional<Task> findTasksByUserAndId(AppUser appUser, Long id);

    Task findByIdAndAppUserId(Long taskId, Long appUserId);

    List<Task> findByAppUser(AppUser appUser);

    Optional<Task> findByIdAndAppUser(Long taskId, AppUser appUser);
}
