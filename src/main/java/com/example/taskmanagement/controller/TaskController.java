package com.example.taskmanagement.controller;

import com.example.taskmanagement.bo.CODE;
import com.example.taskmanagement.bo.Response;
import com.example.taskmanagement.bo.ResponseList;
import com.example.taskmanagement.bo.task.CreateTaskRequest;
import com.example.taskmanagement.bo.task.TaskResponse;
import com.example.taskmanagement.bo.task.UpdateTaskRequest;
import com.example.taskmanagement.service.task.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v2/user/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Response<TaskResponse>> create(@RequestBody CreateTaskRequest request) {

        return new ResponseEntity<>(Response.<TaskResponse>builder()
                .data(taskService.createTask(request))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<TaskResponse>> update(@RequestBody UpdateTaskRequest request, @PathVariable Long id) {


        return new ResponseEntity<>(Response.<TaskResponse>builder()
                .data(taskService.updateTask(request, id))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build(), HttpStatus.OK);
    }

    @GetMapping
    public Response<List<TaskResponse>> retrieveTasks(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(required = false, defaultValue = "20") Integer size,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
                                                      @RequestParam(required = false) String keyword) {
        ResponseList<TaskResponse> responseList = taskService.retrieveTasks(dueDate, keyword, page, size);
        return Response.<List<TaskResponse>>builder()
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .data(responseList.getResponse())
                .allRecords(responseList.getTotalElements())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<TaskResponse>> getById(@PathVariable Long id) {
        Response<TaskResponse> response = Response.<TaskResponse>builder()
                .data(taskService.retrieveTaskById(id))
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
