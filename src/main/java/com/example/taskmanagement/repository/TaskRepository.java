package com.example.taskmanagement.repository;


import com.example.taskmanagement.modle.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT t FROM #{#entityName} t WHERE " +
            "( CAST(:dueDate AS java.time.LocalDate) IS NULL or t.dueDate = :dueDate) AND t.user.id = :userId")
    Page<TaskEntity> retrieveMyTasks(LocalDate dueDate, Long userId,  Pageable pageable);
    @Query("SELECT t FROM #{#entityName} t WHERE t.id= :taskId AND t.user.id = :userId")
    Optional<TaskEntity> retrieveMyTaskById(Long taskId, Long userId);

}