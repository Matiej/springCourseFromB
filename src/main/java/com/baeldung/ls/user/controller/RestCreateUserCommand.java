package com.baeldung.ls.user.controller;

import com.baeldung.ls.user.application.command.CreateUserCommand;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class RestCreateUserCommand {
    @NotBlank(message = "Field 'name' can't be blank, empty or null!")
    private String name;
    @NotBlank(message = "Field 'password' can't be blank, empty or null!")
    private String password;
    private List<String> roles;

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public CreateUserCommand toCreateUserCommand() {
        return new CreateUserCommand(this.name, this.password, this.password, roles);
    }
}
