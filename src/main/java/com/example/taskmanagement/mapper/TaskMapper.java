package com.example.taskmanagement.mapper;

import com.example.taskmanagement.bo.task.CreateTaskRequest;
import com.example.taskmanagement.bo.task.TaskResponse;
import com.example.taskmanagement.modle.TaskEntity;
import com.example.taskmanagement.modle.UserEntity;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.service.auth.UserDetailsUtil;

public class TaskMapper {
    private final UserRepository userRepository;

    public TaskMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public TaskEntity mapRequestToEntity(CreateTaskRequest request) {
        UserEntity userEntity = userRepository.findById(UserDetailsUtil.userDetails().getId()).orElse(null);
        return TaskEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .user(userEntity)
                .dueDate(request.getDueDate())
                .build();
    }

    public TaskResponse mapEntityToResponse(TaskEntity entity) {
        return TaskResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }
}
