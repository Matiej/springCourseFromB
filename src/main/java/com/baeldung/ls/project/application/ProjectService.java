package com.baeldung.ls.project.application;

import com.baeldung.ls.project.application.command.CreateProjectCommand;
import com.baeldung.ls.project.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
//    void createProjectWithTask() throws TaskNotSavedException;
    Optional<Project> findById(Long id);

    Project save(CreateProjectCommand createProjectCommand);
    Project save(Project project);

    Project update(Project project);

    List<Project> findAll();
}
