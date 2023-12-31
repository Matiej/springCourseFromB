package com.baeldung.ls.task.controller;

import com.baeldung.ls.global.headerfactory.HeaderCustomKey;
import com.baeldung.ls.global.headerfactory.HttpHeadersFactory;
import com.baeldung.ls.task.application.TaskService;
import com.baeldung.ls.task.domain.Task;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/api/tasks")
public class RestTaskController {
    private static final String TASKS_PATH = "api/tasks";

    private final TaskService taskService;

    public RestTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<TaskDto>> getAllProjects() {
        List<Task> taskList = taskService.findAll();
        return ResponseEntity.ok()
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                .body(TaskDto.convertToListTaskDto(taskList));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/{id}")
    ResponseEntity<TaskDto> addTaskToProject(@PathVariable("id") @NotNull(message = "Project ID variable can't be null")
                                             @Min(value = 1, message = "Project ID variable mu be greater then zero") Long id,
                                             @Valid @RequestBody RestCreateTaskCommand command) {
        Task savedTask = taskService.save(command.toCreateTaskCommand(id));
        TaskDto taskDto = TaskDto.convertToTaskDto(savedTask);

        return ResponseEntity.created(getUri(savedTask.getId()))
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.CREATED, HttpMethod.POST))
                .build();
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<TaskDto> findTaskById(@PathVariable(name = "id") @NotNull(message = "Task ID variable can't be null")
                                         @Min(value = 1, message = "Task ID variable must be greater then zero") Long id) {
        return taskService.findById(id)
                .map(task -> ResponseEntity.ok()
                        .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                        .body(TaskDto.convertToTaskDto(task)))
                .orElseGet(() -> ResponseEntity.notFound()
                        .header(HeaderCustomKey.STATUS.getHeaderKeyLabel(), HttpStatus.NOT_FOUND.name())
                        .header(HeaderCustomKey.MESSAGE.getHeaderKeyLabel(), "Project with ID: " + id + " not found!")
                        .build());
    }

    private static URI getUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path(TASKS_PATH)
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
