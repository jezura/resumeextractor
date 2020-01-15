package firemni_system.services;


import firemni_system.dao.ContractorRepository;
import firemni_system.workers.Contractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PersonService {
    @Autowired
private ContractorRepository contractorRepository;
 public Collection<Contractor> findAllContractors(){
     List<Contractor> contractors = new ArrayList<Contractor>();
     for (Contractor contractor :contractorRepository.findAll())
     {
         contractors.add(contractor);
     }
     return contractors;
 }

    public void delete(int id){
       contractorRepository.deleteById(id);
    }



}
