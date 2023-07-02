package com.baeldung.ls.task.database;

import com.baeldung.ls.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t where t.name like %?1%")
    List<Task> findByNameMatches(String name);

}
