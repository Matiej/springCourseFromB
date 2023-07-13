package com.baeldung.ls.project.database.impl;

import com.baeldung.ls.project.database.IProjectEmRepository;
import com.baeldung.ls.project.domain.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class IProjectEmRepositoryImpl implements IProjectEmRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Project save(Project project) {
        em.persist(project);
        return project;
    }

    @Transactional
    @Override
    public Optional<Project> findById(Long id) {
        return Optional.ofNullable(em.find(Project.class, id));
    }
}
