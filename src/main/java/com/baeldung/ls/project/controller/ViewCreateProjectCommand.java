package com.baeldung.ls.project.controller;

import com.baeldung.ls.project.application.command.CreateProjectCommand;
import jakarta.validation.constraints.NotBlank;

public class ViewCreateProjectCommand {
    @NotBlank(message = "Field 'projectName' can't be blank, empty or null!")
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public CreateProjectCommand toCreateProjectCommand() {
        return new CreateProjectCommand(this.projectName);
    }

    @Override
    public String toString() {
        return "ViewCreateProjectCommand{" +
                "projectName='" + projectName + '\'' +
                '}';
    }
}
