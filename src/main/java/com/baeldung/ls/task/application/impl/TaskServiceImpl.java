package com.baeldung.ls.task.application.impl;

import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.domain.Project;
import com.baeldung.ls.task.TaskNotSavedException;
import com.baeldung.ls.task.application.TaskService;
import com.baeldung.ls.task.application.command.CreateTaskCommand;
import com.baeldung.ls.task.database.TaskRepository;
import com.baeldung.ls.task.domain.Task;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final Logger LOG = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository repository;
    private final ProjectService projectService;

    public TaskServiceImpl(TaskRepository repository, ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }

    @Transactional(rollbackOn = IllegalArgumentException.class)
    @Override
    public Task save(CreateTaskCommand command) {
        return projectService.findById(command.getProjectId())
                .map(project -> {
                    Task savedTask = repository.save(command.toTask());
                    project.addTask(savedTask);
                    Project savedProject = projectService.update(project);
                    LOG.info("Project id: {} fully updated. Added task: {}, actual tasks in project: {}",
                            project.getId(), savedTask.getName(), project.getTasks().size());
                    return savedTask;
                })
                .orElseThrow(() -> new IllegalArgumentException("Can't find project ID:" + command.getProjectId()));

    }

    @Override
    public Task saveWithCustomCheckedException(Task task) throws TaskNotSavedException {
        //to avoid inconsistent database state when checked exception is thrown,
        // should use transactional with param like here Transactional(rollbackFor = TaskNotSavedException.class)
        throw new TaskNotSavedException("Unable to save task - testing transactional");
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }
}
