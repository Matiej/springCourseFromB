package com.baeldung.ls.project.application.impl;

import com.baeldung.ls.project.application.IProjectService;
import com.baeldung.ls.project.database.IProjectRepository;
import com.baeldung.ls.project.domain.Project;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectServiceImpl implements IProjectService {

    private IProjectRepository projectRepository;

    public ProjectServiceImpl(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

}
