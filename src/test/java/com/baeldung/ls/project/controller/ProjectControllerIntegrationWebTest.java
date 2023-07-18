package com.baeldung.ls.project.controller;

import com.baeldung.ls.project.ProjectRepositoryTestBase;
import com.baeldung.ls.project.application.ProjectService;
import com.baeldung.ls.project.domain.Project;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProjectControllerIntegrationWebTest extends ProjectRepositoryTestBase {
    private final Logger LOG = LoggerFactory.getLogger(ProjectControllerIntegrationWebTest.class);
    private static final String PROJECTS_MAPPING = "/api/projects";
    private MockMvc mockMvc;
    @MockBean
    private ProjectService projectService;
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


}