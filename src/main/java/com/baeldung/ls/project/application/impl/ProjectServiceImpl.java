package com.baeldung.ls.project.application.impl;

import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.application.command.CreateProjectCommand;
import com.baeldung.ls.project.database.IProjectRepository;
import com.baeldung.ls.project.domain.Project;
import com.baeldung.ls.task.TaskNotSavedException;
import com.baeldung.ls.task.application.TaskService;
import com.baeldung.ls.task.domain.Task;
import com.baeldung.ls.task.domain.TaskStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final IProjectRepository projectRepository;
    private final TaskService taskService;

    public ProjectServiceImpl(IProjectRepository projectRepository, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
    }


    @Transactional(rollbackOn = TaskNotSavedException.class)
    @Override
    public void createProjectWithTask() throws TaskNotSavedException {
        //all these operations can be executed by just saving Project entity with tasks in it.
        //here only to show steps
        Project project = new Project("Project_1");
        Project savedProject = projectRepository.save(project);

        Task task1 = new Task("Task_2", "Task 2 for super Project1", LocalDate.now().plusWeeks(4), TaskStatus.NEW);
//        Task savedTask = taskService.save(task1);
        Task savedTask = taskService.save(task1);

        Set<Task> tasks = new HashSet<>();
        tasks.add(savedTask);

        savedProject.setTasks(tasks);
        Project savedProjectWithTasks = projectRepository.save(savedProject);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project save(CreateProjectCommand command) {
        return projectRepository.save(command.toProject());
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

}
