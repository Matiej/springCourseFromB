package com.baeldung.ls.project.application.impl;

import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.application.command.CreateProjectCommand;
import com.baeldung.ls.project.database.IProjectRepository;
import com.baeldung.ls.project.domain.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final IProjectRepository projectRepository;

    public ProjectServiceImpl(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

//    @Transactional(rollbackOn = TaskNotSavedException.class)
//    @Override
//    public void createProjectWithTask() throws TaskNotSavedException {
//        //all these operations can be executed by just saving Project entity with tasks in it.
//        //here only to show steps
//        Project project = new Project("Project_1");
//        Project savedProject = projectRepository.save(project);
//
//        Task task1 = new Task("Task_2", "Task 2 for super Project1", LocalDate.now().plusWeeks(4), TaskStatus.NEW);
////        Task savedTask = taskService.save(task1);
//        Task savedTask = taskService.save(task1);
//
//        Set<Task> tasks = new HashSet<>();
//        tasks.add(savedTask);
//
//        savedProject.setTasks(tasks);
//        Project savedProjectWithTasks = projectRepository.save(savedProject);
//    }


    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project save(CreateProjectCommand command) {
        return projectRepository.save(command.toProject());
    }

    @Override
    public Project update(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

}
