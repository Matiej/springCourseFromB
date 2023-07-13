package com.baeldung.ls.project.application;

import com.baeldung.ls.project.domain.Project;

import java.util.Optional;

public interface IProjectService {
    Optional<Project> findById(Long id);

    Project save(Project project);
}
