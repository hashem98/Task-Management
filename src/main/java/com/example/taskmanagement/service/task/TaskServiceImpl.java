package com.example.taskmanagement.service.task;

import com.example.taskmanagement.bo.ResponseList;
import com.example.taskmanagement.bo.task.CreateTaskRequest;
import com.example.taskmanagement.bo.task.TaskResponse;
import com.example.taskmanagement.bo.task.UpdateTaskRequest;
import com.example.taskmanagement.exceptions.NotFoundException;
import com.example.taskmanagement.mapper.TaskMapper;
import com.example.taskmanagement.modle.TaskEntity;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.service.auth.UserDetailsUtil;
import com.example.taskmanagement.validators.CompositeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.taskmanagement.validators.CompositeValidator.hasValue;
import static com.example.taskmanagement.validators.CompositeValidator.joinViolations;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = new TaskMapper(userRepository);
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest request) {
        preCreateValidation(request);
        TaskEntity savedTask = taskRepository.save(taskMapper.mapRequestToEntity(request));
        return taskMapper.mapEntityToResponse(savedTask);
    }

    private void preCreateValidation(CreateTaskRequest request) {
        List<String> violations = new CompositeValidator<CreateTaskRequest, String>()
                .addValidator(r -> nonNull(r.getTitle()), "Title Cannot Be Empty")
                .addValidator(r -> nonNull(r.getDescription()), "Description  Cannot Be Empty")
                .addValidator(r -> nonNull(r.getStatus()), "Status  Cannot Be Empty")
                .addValidator(r -> isNull(r.getDueDate()) || r.getDueDate().isAfter(LocalDate.now()), "Due Date  Cannot Be in Past")
                .addValidator(r -> nonNull(r.getDueDate()), "Due Date  Cannot Be Empty")
                .validate(request);
        joinViolations(violations);

    }

    @Override
    public TaskResponse updateTask(UpdateTaskRequest request, Long id) {
        preUpdateValidation(request);

        TaskEntity taskEntity = taskRepository.retrieveMyTaskById(id, UserDetailsUtil.userDetails().getId())
                .orElseThrow(() -> new NotFoundException("There is no task with id ==> " + id));

        updateTaskProps(request, taskEntity);

        TaskEntity updatedTask = taskRepository.save(taskEntity);

        return taskMapper.mapEntityToResponse(updatedTask);
    }

    private void updateTaskProps(UpdateTaskRequest request, TaskEntity taskEntity) {
        taskEntity.setTitle(hasValue(request.getTitle()) ? request.getTitle() : taskEntity.getTitle());
        taskEntity.setDescription(hasValue(request.getDescription()) ? request.getDescription() : taskEntity.getDescription());
        taskEntity.setStatus(nonNull(request.getStatus()) ? request.getStatus() : taskEntity.getStatus());
        taskEntity.setDueDate(nonNull(request.getDueDate()) ? request.getDueDate() : taskEntity.getDueDate());
    }

    private void preUpdateValidation(UpdateTaskRequest request) {
        List<String> violations = new CompositeValidator<UpdateTaskRequest, String>()
                .addValidator(r -> isNull(r.getDueDate()) || r.getDueDate().isAfter(LocalDate.now()), "Due Date  Cannot Be in Past")
                .validate(request);
        joinViolations(violations);
    }

    @Override
    public TaskResponse retrieveTaskById(Long id) {
        TaskEntity taskEntity = taskRepository.retrieveMyTaskById(id, UserDetailsUtil.userDetails().getId())
                .orElseThrow(() -> new NotFoundException("You don't have task with id ==> " + id));
        return taskMapper.mapEntityToResponse(taskEntity);

    }

    @Override
    public ResponseList<TaskResponse> retrieveTasks(LocalDate dueDate, Integer page, Integer size) {

        PageRequest pageRequest = PageRequest.of(page + 1, size, Sort.Direction.ASC, "id");
        Pageable pageable = pageRequest.previous();

        Page<TaskEntity> allPageable = taskRepository.retrieveMyTasks(dueDate,
                UserDetailsUtil.userDetails().getId(), pageable);

        List<TaskResponse> data = allPageable.stream()
                .map(taskMapper::mapEntityToResponse)
                .collect(Collectors.toList());

        return ResponseList.<TaskResponse>builder()
                .response(data)
                .totalElements(allPageable.getTotalElements())
                .build();
    }

}
