package com.baeldung.ls.project.controller;

import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.domain.Project;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    String addProject(@Valid @ModelAttribute("project") ViewCreateProjectCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-project";
        }
        Project createdProject = projectService.save(command.toCreateProjectCommand());
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
