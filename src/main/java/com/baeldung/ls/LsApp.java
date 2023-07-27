package com.baeldung.ls;

import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.task.application.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;

@EnableJpaAuditing
@SpringBootApplication
public class LsApp implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(LsApp.class);

    public static void main(final String... args) {
        SpringApplication.run(LsApp.class, args);
    }

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @PostConstruct
//    public void postConstruct() {
//        try {
//            projectService.createProjectWithTask();
//        } catch (Exception e) {
//            LOG.error("Error occurred in creating project with tasks", e);
//        }
//
//        LOG.info("Fetching all Projects");
//        projectService.findAll()
//                .forEach(p -> LOG.info(p.toString()));
//
//        LOG.info("Fetching all tasks");
//        taskService.findAll()
//                .forEach(t -> LOG.info(t.toString()));
//    }


    @Override
    public void run(String... args) throws Exception {
        if(!isTableExists("projectjdbc")) {
            jdbcTemplate.execute("CREATE TABLE projectjdbc(id SERIAL, name VARCHAR(255), date_created DATE)");
        }
    }

    private boolean isTableExists(String tableName) {
        String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = ? AND table_name = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(query, new Object[]{ "learn-spring-database", tableName }, Integer.class);
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
