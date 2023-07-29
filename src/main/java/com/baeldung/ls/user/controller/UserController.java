package com.baeldung.ls.user.controller;

import com.baeldung.ls.global.headerfactory.HeaderCustomKey;
import com.baeldung.ls.global.headerfactory.HttpHeadersFactory;
import com.baeldung.ls.task.controller.TaskDto;
import com.baeldung.ls.user.application.UserService;
import com.baeldung.ls.user.domain.UserEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
        UserEntity user = userService.save(command.toCreateUserCommand());

        return ResponseEntity.created(getUri(user.getId()))
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.CREATED, HttpMethod.POST))
                .build();
    }

    @GetMapping
    ResponseEntity<List<RestUserResponse>> getAllUsers() {
        List<UserEntity> userResponses = userService.findAll();
        return ResponseEntity.ok()
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                .body(RestUserResponse.toRestUserResponse(userResponses));
    }

    @GetMapping(value = "/id")
    ResponseEntity<RestUserResponse> findUserById(@PathVariable("id") @NotNull(message = "UserId filed can't be null")
                                                  @Min(value = 1, message = "UserId filed must be greater then 0") Long id) {
        return userService.findUserById(id).map(user -> ResponseEntity.ok()
                        .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                        .body(RestUserResponse.toRestUserResponse(user)))
                .orElseGet(() -> ResponseEntity.notFound()
                        .header(HeaderCustomKey.STATUS.getHeaderKeyLabel(), HttpStatus.NOT_FOUND.name())
                        .header(HeaderCustomKey.MESSAGE.getHeaderKeyLabel(), "User with ID: " + id + " not found!")
                        .build());
    }

    @DeleteMapping(value = "/id")
    ResponseEntity<Void> removeUserById(@PathVariable("id") @NotNull(message = "UserId filed can't be null")
                                        @Min(value = 1, message = "UserId filed must be greater then 0") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
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
