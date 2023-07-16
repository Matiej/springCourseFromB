package com.baeldung.ls.project.database;

import com.baeldung.ls.project.domain.Project;
import com.baeldung.ls.project.domain.ProjectJdbc;

import java.util.Optional;

public interface ProjectJdbcTemplateRepository {

    Optional<ProjectJdbc> findById(Long id);
    ProjectJdbc save(ProjectJdbc project);
}
