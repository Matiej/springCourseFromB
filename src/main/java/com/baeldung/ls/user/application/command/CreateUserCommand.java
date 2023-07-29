package com.baeldung.ls.user.application.command;

import com.baeldung.ls.user.domain.Role;
import com.baeldung.ls.user.domain.UserEntity;

import java.util.List;
import java.util.Set;

public class CreateUserCommand {
    private String name;
    private String password;
    private String matchingPassword;
    private List<String> roles;

    public CreateUserCommand(String name, String password, String matchingPassword, List<String> roles) {
        this.name = name;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.roles = roles;
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

    public List<String> getRoles() {
        return roles;
    }

    public UserEntity toUserEntity(Set<Role> roles) {
        UserEntity userEntity = new UserEntity(this.name, this.password, this.matchingPassword);
        userEntity.setRoles(roles);
        return userEntity;
    }
}
