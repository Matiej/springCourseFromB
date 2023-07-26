package com.baeldung.ls.global.loggingaspect;

import com.baeldung.ls.project.application.command.CreateProjectCommand;
import com.baeldung.ls.project.domain.Project;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class ProjectServiceLoggingAspect {
    private final static Logger LOG = LoggerFactory.getLogger(ProjectServiceLoggingAspect.class);

    @Before("execution(* com.baeldung.ls.project.application.impl.ProjectServiceImpl.findById(Long))")
    public void beforeFinById(JoinPoint joinPoint) {
        LOG.info("Searching for Project with id {} ", joinPoint.getArgs()[0]);
    }

    @AfterReturning(pointcut = "execution(*..Optional<*..Project> *..ProjectServiceImpl.findById(Long)) && args(id)",
            returning = "project")
    public void afterFindById(JoinPoint joinPoint, Optional<Project> project, Long id) {
        project.ifPresentOrElse(pro -> LOG.info("Project found: {}", pro),
                () -> {
                    LOG.info("Project id: {} not found!", id);
                });
    }

    @Before("execution(* com.baeldung.ls.project.application.impl.ProjectServiceImpl.findAll())")
    public void beforeFindAll(JoinPoint joinPoint) {
        LOG.info("Search for all Projects");
    }

    @AfterReturning(pointcut = "execution(*..List<*..Project> *..ProjectServiceImpl.findAll())",
            returning = "projects")
    public void afterFindById(List<Project> projects) {
        if (!projects.isEmpty()) {
            LOG.info("Found {} projects", projects.size());
        } else {
            LOG.info("No projects found!");
        }
    }

    @After("within(*..ProjectServiceImpl)")
    public void afterAllMethodsOfProjectServiceImpl(JoinPoint joinPoint) {
        LOG.info("After Invoking the method: {} ", joinPoint.getSignature().getName());
    }

    @Around("execution(* com..ProjectServiceImpl.save(*))")
    public Object aroundSave(ProceedingJoinPoint joinPoint) {
        Object value = joinPoint.getArgs()[0];
        try {
            LOG.info("Trying to save project {}, ", value instanceof CreateProjectCommand ? ((CreateProjectCommand) value).getProjectName() : value);
            value = joinPoint.proceed();
            LOG.info("Project saved successfully, id: {}", value instanceof Project ? ((Project) value).getId() : value);
        } catch (Throwable e) {
            LOG.error("Error while saving Project: ", e);
        }
        return value;
    }

    @Around("execution(* com..ProjectServiceImpl.update(*))")
    public Object aroundUpdate(ProceedingJoinPoint joinPoint) {
        Object value = joinPoint.getArgs()[0];
        try {
            LOG.info("Trying to update project id: {}, ", ((Project)value).getId());
            value = joinPoint.proceed();
            LOG.info("Project updated successfully");
        } catch (Throwable e) {
            LOG.error("Error while updating Project: ", e);
        }
        return value;
    }

}
