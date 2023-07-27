package com.baeldung.ls.project.controller;

import com.baeldung.ls.project.ProjectRepositoryTestBase;
import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.application.command.CreateProjectCommand;
import com.baeldung.ls.project.domain.Project;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
class ProjectControllerIntegrationWebTest extends ProjectRepositoryTestBase {
    private final Logger LOG = LoggerFactory.getLogger(ProjectControllerIntegrationWebTest.class);
    private static final String PROJECTS_MAPPING = "/api/projects";
    private MockMvc mockMvc;
    @MockBean
    private ProjectService projectService;

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup(TestInfo info) {
        LOG.info(START_TEST_MESSAGE, info.getDisplayName());
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @AfterEach
    void tierDown(TestInfo testInfo) {
        LOG.info(FINISH_TEST_MESSAGE, testInfo.getDisplayName());
    }

    @Test
    @DisplayName("should getAllProjects() perform GET method and gives back 200code response and given projects json format ")
    void givenProjects_whengetAllProjects_then200code_and_thenSuccess() throws Exception {
        //given
        List<Project> projectList = prepareTestProjects(5);

        //when
        when(projectService.findAll()).thenReturn(projectList);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.get(PROJECTS_MAPPING))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(jsonInString(projectList)));

        //then
        verify(projectService, times(1)).findAll();
        verifyNoMoreInteractions(projectService);
    }

    @Test
    @DisplayName("should getAllProjects() NOT perform GET method, given wrong uri and gives back 404coed response")
    void givenProjects_whenGetAllProjects_thenWrongUir_then404Code_response() throws Exception {
        //given
        List<Project> projectList = prepareTestProjects(5);

        //when
        when(projectService.findAll()).thenReturn(projectList);

        //expect
        mockMvc.perform(MockMvcRequestBuilders.get(PROJECTS_MAPPING + "/badUri/verybadUri"))
                .andExpect(status().is(404))
                .andDo(print())
                .andReturn();

        //then
        verify(projectService, times(0)).findAll();
        verifyNoMoreInteractions(projectService);
    }

    @Test
    @DisplayName("should save() method save given project and gives back 200code with success")
    void whenSave_givenProject_thenSuccess_thenCode200() throws Exception {
        //given
        RestCreateProjectCommand restCreateProjectCommand = prepareRestCommand();
        CreateProjectCommand command = restCreateProjectCommand.toCreateProjectCommand();
        Project project = new Project(command.getProjectName());
        project.setId(1L);

        //when
        when(projectService.save(any())).thenReturn(project);
        URI uri = prepareUriExpectedLocation(PROJECTS_MAPPING, project.getId());

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post(PROJECTS_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON).content(jsonInString(restCreateProjectCommand)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, uri.toString()))
                .andExpect(header().string("Status", HttpStatus.CREATED.name()))
                .andExpect(header().string("Message", "Successful"))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, allowedMethods(HttpMethod.POST)))
                .andReturn();

        //then
        verify(projectService, times(1)).save(any());
        verifyNoMoreInteractions(projectService);

    }

    private URI prepareUriExpectedLocation(String path, Long id) {
        return ServletUriComponentsBuilder
                .fromContextPath(request)
                .path(path)
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}