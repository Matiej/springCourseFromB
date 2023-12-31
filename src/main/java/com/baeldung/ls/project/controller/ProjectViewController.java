package com.baeldung.ls.project.controller;

import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.domain.Project;
import com.baeldung.ls.project.events.ProjectCreatedEvent;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/view/projects")
public class ProjectViewController {
    private final Logger LOG = LoggerFactory.getLogger(ProjectViewController.class);

    private final ProjectService projectService;
    private final ApplicationEventPublisher publisher;

    public ProjectViewController(ProjectService projectService, ApplicationEventPublisher publisher) {
        this.projectService = projectService;
        this.publisher = publisher;
    }

    @GetMapping("/all")
    String getAllProjects(Model model) {
        List<ProjectDto> projectDtoList = ProjectDto.convertToProjectDtoList(projectService.findAll());
        model.addAttribute("projects", projectDtoList);
        LOG.info("Fetched : {} of projects in thymeleaf controller", projectDtoList == null ? 0 : projectDtoList.size());
        return "projects";
    }

    @GetMapping("/allfm")
    String getAllProjectsFreeMarker(Model model) {
        List<ProjectDto> projectDtoList = ProjectDto.convertToProjectDtoList(projectService.findAll());
        model.addAttribute("projects", projectDtoList);
        LOG.info("Fetched : {} of projects in freemarker controller", projectDtoList == null ? 0 : projectDtoList.size());

        return "projectsfm";
    }

    @GetMapping("/add")
    String newProject(Model model) {
        model.addAttribute("project", new ViewCreateProjectCommand());

        return "add-project";
    }

    @PostMapping
    String addProject(@Valid @ModelAttribute("project") ViewCreateProjectCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-project";
        }
        Project createdProject = projectService.save(command.toCreateProjectCommand());
        publisher.publishEvent(new ProjectCreatedEvent(createdProject.getId()));
        return "redirect:/view/projects/all";


    }

    @GetMapping("/home")
    String home(Model model) {
        return "index";
    }

    @GetMapping("/error")
    String error(Model model) {
        Map<String, Object> map = model.asMap();
        return "error";
    }

}
