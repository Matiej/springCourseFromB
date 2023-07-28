package com.baeldung.ls.user.controller;

import com.baeldung.ls.global.headerfactory.HttpHeadersFactory;
import com.baeldung.ls.task.controller.TaskDto;
import com.baeldung.ls.task.domain.Task;
import com.baeldung.ls.user.application.UserService;
import com.baeldung.ls.user.application.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {
    private static final String USERS_PATH = "api/users";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<Void> addUser(@Valid @RequestBody RestCreateUserCommand command) {
        UserResponse userResponse = userService.save(command.toCreateUserCommand());

        return ResponseEntity.created(getUri(userResponse.getUserId()))
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.CREATED, HttpMethod.POST))
                .build();
    }

    @GetMapping
    ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.findAll();
        return ResponseEntity.ok()
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                .body(userResponses);
    }

    private static URI getUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path(USERS_PATH)
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
