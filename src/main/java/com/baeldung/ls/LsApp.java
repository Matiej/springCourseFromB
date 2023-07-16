package com.baeldung.ls;

import com.baeldung.ls.project.application.IProjectService;
import com.baeldung.ls.task.application.TaskService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class LsApp implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(LsApp.class);

    public static void main(final String... args) {
        SpringApplication.run(LsApp.class, args);
    }

    @Autowired
    private IProjectService projectService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void postConstruct() {
        try {
            projectService.createProjectWithTask();
        } catch (Exception e) {
            LOG.error("Error occurred in creating project with tasks", e);
        }

        LOG.info("Fetching all Projects");
        projectService.findAll()
                .forEach(p -> LOG.info(p.toString()));

        LOG.info("Fetching all tasks");
        taskService.findAll()
                .forEach(t -> LOG.info(t.toString()));
    }


    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("CREATE TABLE projectjdbc(id SERIAL, name VARCHAR(255), date_created DATE)");
    }
}
