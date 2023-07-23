package com.baeldung.ls.task.controller;

import com.baeldung.ls.task.application.command.CreateTaskCommand;
import jakarta.validation.constraints.NotBlank;

public class RestCreateTaskCommand {
    @NotBlank(message = "Field 'name' can't be blank, empty or null!")
    private String name;
    @NotBlank(message = "Field 'description' can't be blank, empty or null!")
    private String description;

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

    public CreateTaskCommand toCreateTaskCommand(Long id) {
        return new CreateTaskCommand(this.name, this.description, id);
    }

}
