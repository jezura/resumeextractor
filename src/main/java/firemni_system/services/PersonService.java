package firemni_system.services;


import firemni_system.dao.ContractorRepository;
import firemni_system.dao.ValidatorRepository;
import firemni_system.models.Contractor;
import firemni_system.models.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private ContractorRepository contractorRepository;
    @Autowired
    private ValidatorRepository validatorRepository;

    public Collection<Contractor> findAllContractors(){
        List<Contractor> contractors = new ArrayList<Contractor>();
        for (Contractor contractor :contractorRepository.findAll())
        {
            contractors.add(contractor);
        }
        return contractors;
    }

    public Collection<Validator> findAllValidators(){
        List<Validator> validators = new ArrayList<Validator>();
        for (Validator validator :validatorRepository.findAll())
        {
            validators.add(validator);
        }
        return validators;
    }


    public Validator findValidatorByLogin(String login){

        Validator validator = validatorRepository.findValidatorByLogin(login);

        return validator;

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