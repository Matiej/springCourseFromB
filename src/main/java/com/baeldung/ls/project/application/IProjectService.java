package com.baeldung.ls.project.application;

import java.util.Optional;

import com.baeldung.ls.project.domain.Project;

public interface IProjectService {
    Optional<Project> findById(Long id);

    Project save(Project project);
}
