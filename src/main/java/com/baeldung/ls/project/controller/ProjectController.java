package com.baeldung.ls.project.controller;

import com.baeldung.ls.global.headerfactory.HttpHeadersFactory;
import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.domain.Project;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projectList = projectService.findAll();
        return prepareResponseForGetAll(projectList);
    }

    private ResponseEntity<List<Project>> prepareResponseForGetAll(List<Project> projectList) {
        return ResponseEntity.ok()
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                .body(projectList);
    }

    }
