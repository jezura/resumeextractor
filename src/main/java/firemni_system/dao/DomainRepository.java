package firemni_system.dao;


import firemni_system.models.Domain;
import firemni_system.models.Validator;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;


public interface DomainRepository  extends CrudRepository<Domain, Integer> {

    List<Domain> findDomainsByContractor_id(int id);
    List<Domain> findDomainsByValidator_id(int id);
}
