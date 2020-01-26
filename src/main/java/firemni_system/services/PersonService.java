package firemni_system.services;


import firemni_system.dao.ContractorRepository;
import firemni_system.dao.ManagerRepository;
import firemni_system.dao.ValidatorRepository;
import firemni_system.models.Manager;
import firemni_system.models.Validator;
import firemni_system.models.Contractor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class PersonService {
    @Autowired
    private ContractorRepository contractorRepository;
    @Autowired
    private ValidatorRepository validatorRepository;
    @Autowired
    private ManagerRepository managerRepository;

    public Collection<Contractor> findAllContractors(){
        List<Contractor> contractors = new ArrayList<Contractor>();
        for (Contractor contractor :contractorRepository.findAll())
        {
            contractors.add(contractor);
        }
        return contractors;
    }

    public Collection<Contractor> findContractorsByFirstNameLastName(String name){
        List<Contractor> contractors = new ArrayList<Contractor>();
        for (Contractor contractor :contractorRepository.findContractorsByName(name))
        {
            contractors.add(contractor);
        }
        return contractors;
    }
    public Collection<Validator> findValidatorsByFirstNameLastName(String name){
        List<Validator> validators = new ArrayList<Validator>();
        for (Validator validator :validatorRepository.findValidatorsByName(name))
        {
            validators.add(validator);
        }
        return validators;
    }


    public Collection<Validator> findAllValidators(){
        List<Validator> validators = new ArrayList<Validator>();
        for (Validator validator :validatorRepository.findAll())
        {
            validators.add(validator);
        }
        return validators;
    }

    public Collection<Contractor> findContractorsByMentor(int id){
        List<Contractor> contractors = new ArrayList<Contractor>();
        for (Contractor contractor :contractorRepository.findContractorsByMentor_Id(id))
        {
            contractors.add(contractor);
        }
        return contractors;
    }



    public Validator findValidatorByLogin(String login){
        Validator validator = validatorRepository.findValidatorByLogin(login);
        return validator;
    }
    public Contractor findContractorByLogin(String login){
       Contractor contractor = contractorRepository.findContractorByLogin(login);
       return contractor;
    }
    public Manager findManagerByLogin(String login){
        Manager manager = managerRepository.findManagerByLogin(login);
        return manager;
    }

    public boolean isUnique(String login){
        boolean unique = true;

        if(findContractorByLogin(login) != null)
            unique = false;

        if(findValidatorByLogin(login) != null)
            unique = false;

        if(findManagerByLogin(login) != null)
            unique = false;

return unique;

    }

    public void deleteContractor(int id){
        contractorRepository.deleteById(id);
    }

    public void deleteValidator(int id){
        validatorRepository.deleteById(id);
    }

    public void saveContractor(Contractor c){
        contractorRepository.save(c);
    }

    public void saveValidator(Validator v){
        validatorRepository.save(v);
    }

    public Contractor getContractor(int id){
        return contractorRepository.findById(id).get();
    }

    public Validator getValidator(int id){
        return validatorRepository.findById(id).get();
    }
}