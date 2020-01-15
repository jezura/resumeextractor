package services;

import com.uhk.ppro.firemni_system.entity.workers.Contractor;
import dao.ContractorRepository;
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
     List<Contractor> contractors = new ArrayList<>();
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
