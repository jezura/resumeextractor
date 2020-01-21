package firemni_system.services;

import firemni_system.dao.DomainRepository;
import firemni_system.models.Domain;
import firemni_system.models.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DomainService {
    @Autowired
    private DomainRepository domainRepository;

    public Collection<Domain> findAllDomains(){
        List<Domain> domains = new ArrayList<Domain>();
        for (Domain domain :domainRepository.findAll())
        {
            domains.add(domain);
        }
        return domains;
    }

    public void deleteDomain(int id){
        domainRepository.deleteById(id);
    }

    public void saveDomain(Domain d){
        domainRepository.save(d);
    }

    public Domain getDomain(int id){
        return domainRepository.findById(id).get();
    }

    public Collection<Domain> findDomainsForContractorId(int id){

        List<Domain> domains = new ArrayList<Domain>();
        for (Domain domain :domainRepository.findDomainsByContractor_id(id))
        {
            domains.add(domain);
        }
        return domains;
    }

    public Collection<Domain> findDomainsForValidatorId(int id){
        List<Domain> domains = new ArrayList<Domain>();
        for (Domain domain :domainRepository.findDomainsByValidator_id(id))
        {
            domains.add(domain);
        }
        return domains;
    }


}