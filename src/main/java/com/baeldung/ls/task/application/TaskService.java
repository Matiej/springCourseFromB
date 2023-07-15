package com.baeldung.ls.task.application;

import com.baeldung.ls.task.TaskNotSavedException;
import com.baeldung.ls.task.domain.Task;

import java.util.List;

public interface TaskService {

    Task save(Task task);

    Task saveWithCustomCheckedException(Task task) throws TaskNotSavedException;

    List<Task> findAll();
}
