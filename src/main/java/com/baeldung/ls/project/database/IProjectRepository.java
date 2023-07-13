package com.baeldung.ls.project.database;

import java.time.LocalDate;
import java.util.List;

import com.baeldung.ls.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProjectRepository extends JpaRepository <Project, Long> {



    List<Project> findByName(String name);
    List<Project> findByCreatedAtBetween(LocalDate start, LocalDate end);
}
