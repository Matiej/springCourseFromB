package com.baeldung.ls.project.database;

import com.baeldung.ls.project.ProjectRepositoryTestBase;
import com.baeldung.ls.project.domain.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class IProjectEmRepositoryIntegrationTest extends ProjectRepositoryTestBase {

    @Autowired
    private IProjectEmRepository repository;

    @Test
    @DisplayName("should save() method saver project with success")
    void whenSavingNewProject_thenSuccess() {
        //given
        Project project = prepareMyTestProject();

        //when
        Project savedProject = repository.save(project);

        //then
        assertNotNull(savedProject);
        assertNotNull(savedProject.getId());
    }

    @Test
    @DisplayName("should findById() find saved project successful")
    void givenProject_whenFindById_thenSuccess() {
        //given
        Project project = prepareMyTestProject();
        repository.save(project);

        //when
        Optional<Project> optionalProject = repository.findById(project.getId());

        //then
        assertTrue(optionalProject.isPresent());
        assertNotNull(optionalProject.get());
        assertEquals(optionalProject.get().getName(), project.getName());
    }
}