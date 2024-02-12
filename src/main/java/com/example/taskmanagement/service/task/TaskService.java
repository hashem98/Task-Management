package com.example.taskmanagement.service.task;

import com.example.taskmanagement.bo.ResponseList;
import com.example.taskmanagement.bo.task.CreateTaskRequest;
import com.example.taskmanagement.bo.task.TaskResponse;
import com.example.taskmanagement.bo.task.UpdateTaskRequest;

import java.time.LocalDate;

public interface TaskService {
    TaskResponse createTask(CreateTaskRequest taskDTO);
    TaskResponse updateTask(UpdateTaskRequest request, Long id);
    TaskResponse retrieveTaskById(Long id);
    ResponseList<TaskResponse> retrieveTasks(LocalDate dueDate, Integer page, Integer size);
}
