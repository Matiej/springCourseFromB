package com.baeldung.ls.project.database;

import com.baeldung.ls.project.domain.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class IProjectRepositoryIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(IProjectRepositoryIntegrationTest.class);
    private static final LocalDate TEST_PROJECT_DATE = LocalDate.now();

    @Autowired
    private IProjectRepository repository;

    @BeforeEach
    void setUp(TestInfo info) {
        LOG.info("Starting tesT: {}", info.getDisplayName());
    }

    @Test
    @DisplayName("Should save() method save project with success")
    void whenSaveProject_ThenSuccess() {
        //given
        Project myTestProject = prepareMyTestProject();

        //when
        Project saved = repository.save(myTestProject);

        //then
        assertNotNull(saved);
    }

    @Test
    @DisplayName("Should findById() method find saved project with success")
    void givenProject_whenFindById_thenSuccess() {
        //given
        Project savedProject = repository.save(prepareMyTestProject());

        //when
        Optional<Project> projectOptional = repository.findById(savedProject.getId());
        Project projectFound = projectOptional.get();
        assertNotNull(projectFound);

        //then
        assertAll("Check all project properties",
                () -> assertEquals(projectFound.getId(), 1L),
                () -> assertEquals(projectFound.getName(), "My test Project"),
                () -> assertEquals(projectFound.getCreatedAt(), savedProject.getCreatedAt()),
                () -> assertEquals(projectFound, savedProject)
        );
    }

    @Test
    @DisplayName("Should findByName() method return list of two projects with the same name")
    public void givenListOfProject_whenFindByName_thenReturnTwoProjectsList() {
        //given
        repository.save(prepareMyTestProject());
        repository.save(prepareMyTestProject());
        Project otherProject = prepareMyTestProject();
        otherProject.setName("OtherProject");
        repository.save(otherProject);

        //when
        List<Project> projectList = repository.findByName("My test Project");

        //then
        assertNotNull(projectList);
        assertFalse(projectList.isEmpty());
        assertEquals(2, projectList.size());
    }

    @Test
    @DisplayName("should findByCreatedAtBetween() method return list with one project")
    public void givenListOfProjects_whenFindByBetween_thenReturnOneProjectList() {
        //given
        Project project = prepareMyTestProject();
        repository.save(project);
        Project otherProject = prepareMyTestProject();
        otherProject.setName("OtherProject");
        repository.save(otherProject);
        Project myOtherDateProject = prepareMyTestProject();
        myOtherDateProject.setCreatedAt(LocalDate.of(2010, 1, 15));
        repository.save(myOtherDateProject);

        //when
        List<Project> retrivedProjectList = repository.findByCreatedAtBetween(LocalDate.of(2010, 1, 14),
                LocalDate.of(2011, 2, 25));

        //then
        assertNotNull(retrivedProjectList);
        assertFalse(retrivedProjectList.isEmpty());
        assertEquals(1, retrivedProjectList.size());
        assertThat(retrivedProjectList, hasItem(myOtherDateProject));
    }

    private static Project prepareMyTestProject() {
        return new Project("My test Project");
    }
}