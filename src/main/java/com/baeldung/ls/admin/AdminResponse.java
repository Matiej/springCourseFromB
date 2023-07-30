package com.baeldung.ls.admin;

import com.baeldung.ls.project.domain.Project;
import com.baeldung.ls.user.domain.Role;

import java.util.List;
import java.util.Set;

public class AdminResponse {
    private List<Role> roles;
    private Project project;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
