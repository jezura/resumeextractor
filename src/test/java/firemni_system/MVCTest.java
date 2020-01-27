package firemni_system;

import firemni_system.controllers.*;
import firemni_system.dao.ContractorRepository;
import firemni_system.dao.DomainRepository;
import firemni_system.models.*;
import firemni_system.services.DomainService;
import firemni_system.services.PersonService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
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