package com.baeldung.ls.task.application.impl;

import com.baeldung.ls.task.TaskNotSavedException;
import com.baeldung.ls.task.application.TaskService;
import com.baeldung.ls.task.database.TaskRepository;
import com.baeldung.ls.task.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task save(Task task) {
//        throw new RuntimeException("Unable to save task - for test @transactional");
        return repository.save(task);
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
