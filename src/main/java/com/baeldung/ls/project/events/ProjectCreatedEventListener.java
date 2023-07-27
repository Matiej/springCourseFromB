package com.baeldung.ls.project.events;

import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.domain.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProjectCreatedEventListener {
    private final Logger LOG = LoggerFactory.getLogger(ProjectCreatedEventListener.class);
    private final ProjectService projectService;

    public ProjectCreatedEventListener(ProjectService projectService) {
        this.projectService = projectService;
    }

    @EventListener
    public void handleProjectCreatedEvent(ProjectCreatedEvent projectCreatedEvent) {
        projectService.findById(projectCreatedEvent.getProjectId())
                .ifPresentOrElse(project -> LOG.info("Event listener invoking email sending method in order to send important data related to just created projects: {}", project),
                        () -> {
                            LOG.info("Something went wrong, project ID: {} hasn't been created" , projectCreatedEvent.getProjectId());
                        });
    }
}
