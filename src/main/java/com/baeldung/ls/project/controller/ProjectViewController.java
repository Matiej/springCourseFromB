package com.baeldung.ls.project.controller;

import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.domain.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view/projects")
public class ProjectViewController {
    private final Logger LOG = LoggerFactory.getLogger(ProjectViewController.class);

    private final ProjectService projectService;

    public ProjectViewController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/all")
    String getAllProjects(Model model) {
        List<ProjectDto> projectDtoList = ProjectDto.convertToProjectDtoList(projectService.findAll());
        model.addAttribute("projects", projectDtoList);
        LOG.info("Added to model number: {} of projects", projectDtoList == null ? 0 : projectDtoList.size());
        return "projects";
    }

    @GetMapping("/add")
    String newProject(Model model) {
        model.addAttribute("project", new ViewCreateProjectCommand());

        return "add-project";
    }

    @PostMapping
    String addProject(ViewCreateProjectCommand command) {
        Project createdProject = projectService.save(command.toCreateProjectCommand());
        return "redirect:/view/projects/all";
    }

    @GetMapping("/home")
    String home(Model model) {
        return "index";
    }

}
