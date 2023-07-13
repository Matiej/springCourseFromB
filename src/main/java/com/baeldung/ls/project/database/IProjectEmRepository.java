package com.baeldung.ls.project.database;

import com.baeldung.ls.project.domain.Project;

import java.util.Optional;

public interface IProjectEmRepository {

    Project save(Project project);

    Optional<Project> findById(Long id);

}
