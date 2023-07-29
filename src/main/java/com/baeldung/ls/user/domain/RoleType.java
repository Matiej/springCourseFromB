package com.baeldung.ls.user.domain;

public enum RoleType {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), MANAGER("ROLE_MANAGER");

    private final String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
