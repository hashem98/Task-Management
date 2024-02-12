package com.example.taskmanagement.bo.task;

import com.example.taskmanagement.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UpdateTaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
}
