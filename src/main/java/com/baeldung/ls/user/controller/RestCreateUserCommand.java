package com.baeldung.ls.user.controller;

import com.baeldung.ls.user.application.command.CreateUserCommand;
import jakarta.validation.constraints.NotBlank;

public class RestCreateUserCommand {
    @NotBlank(message = "Field 'name' can't be blank, empty or null!")
    private String name;
    @NotBlank(message = "Field 'password' can't be blank, empty or null!")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CreateUserCommand toCreateUserCommand() {
        return new CreateUserCommand(this.name, this.password, this.password);
    }
}
