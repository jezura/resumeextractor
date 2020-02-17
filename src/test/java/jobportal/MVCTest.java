package jobportal;

import jobportal.controllers.*;
import jobportal.dao.ContractorRepository;
import jobportal.dao.DomainRepository;
import jobportal.services.DomainService;
import jobportal.services.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = WorksController.class)
public class MVCTest {

    @Autowired
    private ContractorController contractorController;
    @Autowired
    private ValidatorController validatorController;
    @Autowired
    private DomainController domainController;
    @Autowired
    private FeedbackController feedbackController;
    @Autowired
    private WorksController worksController;
    @Autowired
    private PlanController planController;

    @Autowired
    private PersonService personService;

    @Autowired
    private DomainService domainService;

    @MockBean
    private ContractorRepository contractorRepository;

    @MockBean
    private DomainRepository domainRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/newWork", 1)).andExpect(status().isOk())
                .andExpect(view().name("addWork")).andExpect(model().attributeExists("work", "teams", "workTypes", "domains"));
    }


}