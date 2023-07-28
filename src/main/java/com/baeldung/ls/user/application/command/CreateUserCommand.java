package com.baeldung.ls.user.application.command;

import com.baeldung.ls.user.domain.UserEntity;

public class CreateUserCommand {
    private String name;
    private String password;
    private String matchingPassword;

    public CreateUserCommand(String name, String password, String matchingPassword) {
        this.name = name;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public UserEntity toUserEntity() {
        return new UserEntity(this.name, this.password, this.matchingPassword);
    }
}
