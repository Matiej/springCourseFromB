package com.baeldung.ls.project;

import com.baeldung.ls.project.domain.Project;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class ProjectRepositoryTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectRepositoryTestBase.class);

    @BeforeAll
    static void init(TestInfo testInfo) {
        LOG.info("Start test suite: {}.", testInfo.getDisplayName());
    }

    @AfterAll
    static void afterAllTests(TestInfo testInfo) {
        LOG.info("Finished running the tests suit: {}.", testInfo.getDisplayName());
    }

    protected List<Project> prepareTestProjects(int numberOfProjects) {
        List<Project> projectList = new ArrayList<>();
        for (int i = 0; i < numberOfProjects; i++) {
            projectList.add(new Project(RandomStringUtils.randomAlphabetic(6)));
        }
        return projectList;
    }

    protected Project prepareMyTestProject() {
        return new Project("My test Project");
    }
}
