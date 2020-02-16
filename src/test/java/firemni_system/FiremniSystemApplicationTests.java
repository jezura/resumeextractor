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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FiremniSystemApplicationTests {

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


    // otestuje korektni vytvoreni novych instanci všech kontrolleru systemu
    @Test
    public void controllersLoads() throws Exception {
        assertThat(contractorController).isNotNull();
        assertThat(validatorController).isNotNull();
        assertThat(domainController).isNotNull();
        assertThat(feedbackController).isNotNull();
        assertThat(worksController).isNotNull();
        assertThat(planController).isNotNull();
    }

    @Test
    public void getContractorsTest() throws Exception {
        when(contractorRepository.findAll()).thenReturn(Stream.of(getContractor())
                .collect(Collectors.toList()));
        System.out.println(personService.findAllContractors().size());
        Assert.assertEquals(1, personService.findAllContractors().size());
    }

    @Test
    public void getDomainsTest() throws Exception {
        when(domainRepository.findAll()).thenReturn(Stream.of(getDomain())
                .collect(Collectors.toList()));
        System.out.println(domainService.findAllDomains().size());
        Assert.assertEquals(1, domainService.findAllDomains().size());
    }

    @Test
    public void idsCorrectAssignTest() throws Exception {
        Contractor contractor = getContractor();
        Validator validator = getValidator();
        Team team = getTeam();

        assertThat(team.getName()).isEqualTo(validator.getTeam().getName());
        assertThat(contractor.getMentor().getLogin()).isEqualTo(validator.getLogin());
    }

    @Test
    public void idIsBetweenTest() throws Exception {
        Contractor contractor = getContractor();
        Validator validator = getValidator();
        Team team = getTeam();

        assertThat(team.getId()).isBetween(1,1000000);
    }

    @Test
    public void telephoneNumberFormatTest() throws Exception {
        Contractor contractor = getContractor();
        Validator validator = getValidator();
        Team team = getTeam();

        assertThat(validator.getTelephone().length()).isEqualTo(9);
    }

    /*@Test
    public void getDomainsByNameTest() throws Exception {
        when(domainRepository.findAll()).thenReturn(Stream.of(getDomain())
                .collect(Collectors.toList()));
        System.out.println(domainService.findAllDomains().size());
        Assert.assertEquals(1, domainService.findDomainsByName("seznam.cz").size());

    }*/

   /*@Test
    public void saveDomainTest() throws Exception{
        Domain domain = getDomain();
        when(domainRepository.save(domain)).thenReturn(domain);
        Assert.assertEquals(1, domainRepository.findDomainsByName(domain.getName()).size());
    }*/

    private Team getTeam() {
        Team team = new Team();
        team.setId(1);
        team.setName("Filters");
        return team;
    }

    private Region getSwimlane() {
        Region swimlane = new Region();
        swimlane.setId(1);
        swimlane.setName("Skilled");
        return swimlane;
    }

    private Domain getDomain() {
        Domain domain = new Domain();
        domain.setId(1);
        domain.setContractor(getContractor());
        domain.setValidator(getValidator());
        domain.setDueDate(LocalDate.of(2019, 1, 1));
        domain.setPriority(2);
        domain.setName("seznam.cz");
        return domain;
    }

    private Validator getValidator() {
        Validator validator = new Validator();
        validator.setId(1);
        validator.setLogin("validator");
        validator.setPassword("heslo");
        validator.setFirstName("Validator");
        validator.setLastName("Novak");
        validator.setCity("Hradec Kralove");
        validator.setAddress("Ulice 321");
        validator.setTelephone("123654987");
        validator.setHireDate(LocalDate.of(2019, 1, 1));
        validator.setRole("ROLE_VALIDATOR");
        validator.setTeam(getTeam());

        return validator;
    }

    private Contractor getContractor() {
        Contractor contractor = new Contractor();
        contractor.setId(1);
        contractor.setLogin("contractor");
        contractor.setPassword("heslo");
        contractor.setFirstName("Jmeno");
        contractor.setLastName("Prijmeni");
        contractor.setCity("Trutnov");
        contractor.setAddress("Ulice 123");
        contractor.setTelephone("789654123");
        contractor.setHireDate(LocalDate.of(2015, 12, 12));
        contractor.setRole("ROLE_CONTRACTOR");
        contractor.setTeam(getTeam());
        contractor.setMentor(getValidator());
        contractor.setSwimlane(getSwimlane());

        return  contractor;
    }
}