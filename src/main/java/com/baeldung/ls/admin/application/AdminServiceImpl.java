package com.baeldung.ls.admin.application;

import com.baeldung.ls.admin.AdminResponse;
import com.baeldung.ls.admin.AdminService;
import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.domain.Project;
import com.baeldung.ls.task.domain.Task;
import com.baeldung.ls.task.domain.TaskStatus;
import com.baeldung.ls.user.application.RoleService;
import com.baeldung.ls.user.database.RoleRepository;
import com.baeldung.ls.user.domain.Role;
import com.baeldung.ls.user.domain.RoleType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final RoleService roleService;
    private final ProjectService projectService;
    private final RoleRepository roleRepository;

    public AdminServiceImpl(RoleService roleService, ProjectService projectService,
                            RoleRepository roleRepository) {
        this.roleService = roleService;
        this.projectService = projectService;
        this.roleRepository = roleRepository;
    }

    @Override
    public AdminResponse createRoles() {
        List<Role> savedRoles = Arrays.stream(RoleType.values())
                .map(Role::new)
                .collect(Collectors.toSet())
                .stream()
                .filter(this::isRoleNotExistInDataBase).map(roleRepository::save).toList();
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setRoles(savedRoles);
        return adminResponse;
    }

    private boolean isRoleNotExistInDataBase(Role role) {
        return roleService.findRoleByName(role.getRole()).isEmpty();
    }

    @Override
    public AdminResponse createProjectWithTasks(int numberOfTasks) {
        Project project = new Project("Admin creation project time: " + LocalDateTime.now().withNano(0));
        Set<Task> tasks = new HashSet<>();
        for(int i = 1; i <= numberOfTasks; i++) {
            String taskName = "for Admin project, task number: " + i;
            String desc = "Task auto created for project " + project.getName();
            LocalDate dueDate = LocalDate.now().plusMonths(i);
            Task task = new Task(taskName, desc, dueDate, TaskStatus.NEW);
            tasks.add(task);
        }
        project.setTasks(tasks);
        Project savedProject = projectService.save(project);
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setProject(savedProject);
        return adminResponse;
    }
}
