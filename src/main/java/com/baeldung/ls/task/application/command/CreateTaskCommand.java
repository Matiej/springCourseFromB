package com.baeldung.ls.task.application.command;

import com.baeldung.ls.task.domain.Task;
import com.baeldung.ls.task.domain.TaskStatus;

import java.time.LocalDate;

public class CreateTaskCommand {
    private String name;
    private String description;
    private Long projectId;

    public CreateTaskCommand(String name, String description, Long projectId) {
        this.name = name;
        this.description = description;
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task toTask() {
        return new Task(this.name, this.description, LocalDate.now().plusMonths(2), TaskStatus.NEW);
    }
}
