package com.baeldung.ls.admin.controller;

import com.baeldung.ls.admin.AdminResponse;
import com.baeldung.ls.admin.AdminService;
import com.baeldung.ls.global.headerfactory.HttpHeadersFactory;
import com.baeldung.ls.project.controller.ProjectDto;
import com.baeldung.ls.project.domain.Project;
import com.baeldung.ls.user.domain.Role;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping(value = "/welcome")
    ResponseEntity<?> welcome() {
        return ResponseEntity.ok()
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                .body("WELCOME TO TEST APP. LOGIN PROBABLY WITH SUCCESS");
    }

    @GetMapping(value = "/roles", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<Role>> createRoles() {
        AdminResponse roles = service.createRoles();
        return ResponseEntity.ok()
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                .body(roles.getRoles());
    }

    @GetMapping(value = "/projects/{numberOfTasks}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Project> createProjectWithTasks(@PathVariable(name = "numberOfTasks") @Min(value = 0, message = "Value 'numberOfTasks' must positive number")
                                                   @NotNull(message = "Value 'numberOfTasks' can't be null!!") Integer numberOfTasks) {
        AdminResponse projectWithTasks = service.createProjectWithTasks(numberOfTasks);
        return ResponseEntity.ok()
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                .body(projectWithTasks.getProject());
    }
}
