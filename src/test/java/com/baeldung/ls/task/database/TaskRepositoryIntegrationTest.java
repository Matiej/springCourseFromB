package com.baeldung.ls.task.database;

import com.baeldung.ls.task.domain.Task;
import com.baeldung.ls.task.domain.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaskRepositoryIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(TaskRepositoryIntegrationTest.class);

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp(TestInfo info) {
        LOG.info("Starting tesT: {}", info.getDisplayName());
    }

    @Test
    @DisplayName("Should findByNameMatches(String name) return list of tasks that match for given string")
    void givenListOfTasks_whenFindByNameMatches_thenSuccess() {
        //given
        List<Task> taskList = prepareTestTasks();
        taskRepository.saveAll(taskList);

        //when
        List<Task> retrievedTasks = taskRepository.findByNameMatches("High");

        //then
        assertNotNull(retrievedTasks);
        assertFalse(retrievedTasks.isEmpty());
        assertEquals(2, retrievedTasks.size());

    }

    private static List<Task> prepareTestTasks() {
        Task task1 = new Task("High Priority Task Clinic", "High Priority Task dental clinic",
                LocalDate.now().plusMonths(3), TaskStatus.NEW);

        Task task2 = new Task("Low Priority Task Clinic", "Not important Task dental clinic",
                LocalDate.now().plusMonths(6), TaskStatus.NEW);

        Task task3 = new Task("Medium Priority Task Clinic", "some Task dental clinic, not very important",
                LocalDate.now().plusMonths(6), TaskStatus.IN_PROGRESS);

        Task task4 = new Task("High Priority Task Car Service", "High Priority Task for Car service",
                LocalDate.now().plusMonths(1), TaskStatus.IN_PROGRESS);

        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
        return taskList;
    }
}