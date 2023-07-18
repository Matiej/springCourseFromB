package com.baeldung.ls.project.controller;

import com.baeldung.ls.project.application.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("view/projects")
public class ProjectViewController {
    private final Logger LOG = LoggerFactory.getLogger(ProjectViewController.class);

    private final ProjectService projectService;

    public ProjectViewController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    String getAllProjects(Model model) {
        List<ProjectDto> projectDtoList = ProjectDto.convertToProjectDtoList(projectService.findAll());
        model.addAttribute("projects", projectDtoList);
        LOG.info("Added to model number: {} of projects", projectDtoList == null ? 0 : projectDtoList.size());
        return "projects";
    }

}
