package com.baeldung.ls.project.controller;

import com.baeldung.ls.global.headerfactory.HeaderCustomKey;
import com.baeldung.ls.global.headerfactory.HttpHeadersFactory;
import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.domain.Project;
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
@RequestMapping("/api/projects")
public class ProjectController {
    private static final String PROJECTS_PATH = "projects";
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<Project> projectList = projectService.findAll();
        String name = IllegalArgumentException.class.getSimpleName();
        return prepareResponseForGetAll(projectList);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<ProjectDto> getProjectById(@PathVariable("id") @NotNull(message = "Project ID variable can't be null")
                                           @Min(value = 1, message = "Project ID variable mu be greater then zero") Long id) {
        return projectService.findById(id)
                .map(project -> ResponseEntity.ok()
                        .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                        .body(ProjectDto.convertToProjectDto(project)))
                .orElse(ResponseEntity.notFound()
                        .header(HeaderCustomKey.STATUS.getHeaderKeyLabel(), HttpStatus.NOT_FOUND.name())
                        .header(HeaderCustomKey.MESSAGE.getHeaderKeyLabel(), "Project with ID: " + id + " not found!")
                        .build());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> addProject(@Valid @RequestBody RestCreateProjectCommand restCreateProjectCommand) {
        Project savedProject = projectService.save(restCreateProjectCommand.toCreateProjectCommand());

        return ResponseEntity.created(getUri(savedProject.getId()))
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.CREATED, HttpMethod.POST))
                .build();
    }

    private static URI getUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path(PROJECTS_PATH)
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    private ResponseEntity<List<ProjectDto>> prepareResponseForGetAll(List<Project> projectList) {
        return ResponseEntity.ok()
                .headers(HttpHeadersFactory.getSuccessfulDefaultHeaders(HttpStatus.OK, HttpMethod.GET))
                .body(ProjectDto.convertToProjectDtoList(projectList));
    }


}
