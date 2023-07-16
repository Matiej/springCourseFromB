package com.baeldung.ls.project.application.command;

import com.baeldung.ls.project.domain.Project;

public class CreateProjectCommand {
    private String projectName;

    public CreateProjectCommand(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Project toProject() {
        return new Project(this.projectName);
    }


}
