package com.baeldung.ls.global.loggingaspect;

import com.baeldung.ls.task.domain.Task;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class TaskServiceLoggingAspect {
    private final static Logger LOG = LoggerFactory.getLogger(TaskServiceLoggingAspect.class);

    @Before("execution(* com.baeldung.ls.task.application.impl.TaskServiceImpl.findById(Long))")
    public void beforeFindById(JoinPoint joinPoint) {
        LOG.info("Searching for Task with id {} ", joinPoint.getArgs()[0]);
    }

    //    @AfterReturning(pointcut = "execution(*..Optional<*..Task> *..TaskServiceImpl.findById(Long)) && args(id)", returning = "task")
    @AfterReturning(pointcut = "execution(*..Optional<*..Task> *..TaskServiceImpl.findById(Long)) && args(id)",
            returning = "task")
    public void afterFindById(JoinPoint joinPoint, Optional<Task> task, Long id) {
        task.ifPresentOrElse(t -> LOG.info("Task found: {}", t),
                () -> {
                    LOG.info("Task id: {} not found!", id);
                });
    }

    @Before("execution(* com.baeldung.ls.task.application.impl.TaskServiceImpl.findAll())")
    public void beforeFindAll(JoinPoint joinPoint) {
        LOG.info("Search for all Tasks");
    }

    @AfterReturning(pointcut = "execution(*..List<*..Task> *..TaskServiceImpl.findAll())",
    returning = "taskList")
    public void afterReturningFindAll(List<Task> taskList) {
        if(taskList.isEmpty()) {
            LOG.info("No tasks found!!");
        } else {
            LOG.info("Found {} tasks", taskList.size());
        }
    }

    @After("within(*..TaskServiceImpl)")
    public void afterAllMethodsOfTaskServiceImpl(JoinPoint joinPoint) {
        LOG.info("After Invoking the method: {} ", joinPoint.getSignature().getName());
    }


}
