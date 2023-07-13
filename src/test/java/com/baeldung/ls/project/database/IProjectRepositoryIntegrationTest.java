package com.baeldung.ls.project.database;

import com.baeldung.ls.project.ProjectRepositoryTestBase;
import com.baeldung.ls.project.domain.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class IProjectRepositoryIntegrationTest extends ProjectRepositoryTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(IProjectRepositoryIntegrationTest.class);
    private static final LocalDate TEST_PROJECT_DATE = LocalDate.now();

    @Autowired
    private IProjectRepository repository;

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

    @Test
    @DisplayName("should findAll() paginated method return two pages of size 5 from 152 total elements")
    public void givenDataCreated_whenFindAllPaginated_thenSuccess() {
        //given
        List<Project> projectList = prepareTestProjects(152);
        List<Project> savedProjects = repository.saveAll(projectList);

        //when
        Page<Project> retrievedProjects = repository.findAll(PageRequest.of(2, 5));

        //then
        assertThat(retrievedProjects.getContent(), hasSize(5));
//        assertEquals(retrievedProjects.getTotalPages(), 2);
        assertEquals(retrievedProjects.getTotalElements(), 152L);

    }

    @Test
    @DisplayName("should findAll() sorted method return two pages of size 5 from 152 total elements")
    public void givenDataCreated_whenFindAllSorted_thenSuccess() {
        //given
        List<Project> projectList = prepareTestProjects(152);
        List<Project> savedProjects = repository.saveAll(projectList);

        //when
        List<Project> retrievedProjects = repository.findAll(Sort.by(Sort.Order.desc("id")));

        //then
        assertThat(retrievedProjects, hasSize(152));
        List<Project> copiedSavedProjectsList = new ArrayList<>(savedProjects);
        assertNotEquals(copiedSavedProjectsList, retrievedProjects);
        copiedSavedProjectsList.sort(Comparator.comparing(Project::getId, Comparator.reverseOrder()));
        assertEquals(retrievedProjects, copiedSavedProjectsList);


    }

    @Test
    @DisplayName("should findAll() sorted and paginated method return two pages of size 5 from 152 total elements")
    public void givenDataCreated_whenFindAllSortedPaginated_thenSuccess() {
        //given
        List<Project> projectList = prepareTestProjects(152);
        List<Project> savedProjects = repository.saveAll(projectList);

        //when
        Page<Project> retrievedProjects = repository.findAll(PageRequest.of(2, 5, Sort.by(Sort.Order.desc("id"))));

        //then
        assertThat(retrievedProjects.getContent(), hasSize(5));
        assertEquals(retrievedProjects.getPageable().getPageNumber(), 2);
        assertEquals(retrievedProjects.getTotalElements(), 152L);

        List<Project> copiedSavedProjectsLimitedAndSortedDesc = new ArrayList<>(savedProjects
                .stream()
                .sorted(Comparator.comparing(Project::getId).reversed())
                .limit(10)
                .toList());
        List<Project> allRetrievedSortedProjects = retrievedProjects.getContent();
        
//        assertEquals(allRetrievedSortedProjects, copiedSavedProjectsLimitedAndSortedDesc);


    }


}