package firemni_system.services;

import firemni_system.dao.DomainRepository;
import firemni_system.models.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public void setDomainsForContractorNull(int id){


        for (Domain domain :domainRepository.findDomainsByContractor_id(id))
        {
           domain.setContractor(null);
        }

    }

    public void setDomainsForValidatorNull(int id){
        for (Domain domain :domainRepository.findDomainsByValidator_id(id))
        {
            domain.setValidator(null);
        }
    }

    public Collection<Domain> sortDomainsByDate( Collection<Domain> domainsToSort){
        List domains = new ArrayList(domainsToSort);
        Collections.sort(domains, new Comparator<Domain>() {
            public int compare(Domain d1, Domain d2) {
                return d2.getDueDate().compareTo(d1.getDueDate());
            }
        });
        return domains;
    }

    public Collection<Domain> findDomainsByName(String string){

        List<Domain> domains = new ArrayList<Domain>();
        for (Domain domain :domainRepository.findDomainsByNameIsLike(string))
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

    public Collection<Domain> filterByContractorID(int id, Collection<Domain> filteredDomains){
        List<Domain> domains = new ArrayList<Domain>();
        for (Domain domain :filteredDomains)
        {
            if(domain.getContractor().getId() == id)
                domains.add(domain);
        }
        return domains;
    }

    public Collection<Domain> filterByValidatorID(int id, Collection<Domain> filteredDomains){
        List<Domain> domains = new ArrayList<Domain>();
        for (Domain domain :filteredDomains)
        {
            if(domain.getValidator().getId() == id)
                domains.add(domain);
        }
        return domains;
    }

    public boolean isUnique(String name){
        boolean unique = true;
    if(!domainRepository.findDomainsByName(name).isEmpty())
        unique = false;
        return unique;
    }


}