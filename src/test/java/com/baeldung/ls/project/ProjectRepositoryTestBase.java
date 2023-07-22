package com.baeldung.ls.project;

import com.baeldung.ls.project.application.command.CreateProjectCommand;
import com.baeldung.ls.project.controller.RestCreateProjectCommand;
import com.baeldung.ls.project.domain.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ProjectRepositoryTestBase {
    protected static final String START_TEST_MESSAGE = "Starting test: {}.";
    protected static final String FINISH_TEST_MESSAGE = "Finished test: {}";
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

    protected CreateProjectCommand prepareCreateProjectCommad() {
        return new CreateProjectCommand("My new Super Project");
    }

    protected RestCreateProjectCommand prepareRestCommand() {
        RestCreateProjectCommand restCreateProjectCommand = new RestCreateProjectCommand();
        restCreateProjectCommand.setProjectName("My new fancy PROJECT");
        return restCreateProjectCommand;
    }

    protected String jsonInString(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);

        try {
            jsonInString = mapper.writeValueAsString(object);
//            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonInString;
    }

    protected String allowedMethods(HttpMethod... methods) {
        return Arrays.toString(methods);
    }
}
