package com.baeldung.ls.project.database;

import com.baeldung.ls.project.ProjectRepositoryTestBase;
import com.baeldung.ls.project.domain.Project;
import com.baeldung.ls.project.domain.ProjectJdbc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProjectJdbcTemplateRepositoryIntegrationTest extends ProjectRepositoryTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(IProjectRepositoryIntegrationTest.class);
    private static final LocalDate TEST_PROJECT_DATE = LocalDate.now();

    @Autowired
    private ProjectJdbcTemplateRepository repository;

    @Test
    @DisplayName("Should saved project find when using findById()")
    void givenProject_whenFindById_thenSuccess() {
        //given
        ProjectJdbc p1 = prepareTestProject();
        ProjectJdbc p2 = prepareTestProject();
        ProjectJdbc savedProject1 = repository.save(p1);
        ProjectJdbc savedProject2 = repository.save(p2);

        //when
        Optional<ProjectJdbc> optionalProjectJdbc = repository.findById(2L);

        //then
        assertNotNull(optionalProjectJdbc);
        assertTrue(optionalProjectJdbc.isPresent());
        ProjectJdbc projectJdbc = optionalProjectJdbc.get();
        assertNotNull(projectJdbc);
        assertEquals(projectJdbc.getId(), 2L);

    }

    @Test
    @DisplayName("Should save() method save project with success")
    void whenSaveProject_ThenSuccess() {
        //given
        ProjectJdbc projectJdbc = prepareTestProject();

        //when
        ProjectJdbc saved = repository.save(projectJdbc);

        //then
        assertNotNull(saved);
        assertEquals(saved.getId(), 1L);

    }

    private ProjectJdbc prepareTestProject() {
        return new ProjectJdbc("TestJdbcProject", LocalDate.now());
    }
}