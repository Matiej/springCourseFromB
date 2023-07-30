package com.baeldung.ls.admin;

public interface AdminService {

    AdminResponse createRoles();
    AdminResponse createProjectWithTasks(int numberOfTasks);

}
