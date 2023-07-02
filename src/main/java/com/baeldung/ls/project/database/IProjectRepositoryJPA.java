package com.baeldung.ls.project.database;

import com.baeldung.ls.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProjectRepositoryJPA extends JpaRepository<Project, Long> {


}
