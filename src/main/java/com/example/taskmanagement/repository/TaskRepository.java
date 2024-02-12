package com.example.taskmanagement.repository;


import com.example.taskmanagement.modle.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

}
