package jobportal.dao;


import jobportal.models.Domain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DomainRepository  extends CrudRepository<Domain, Integer> {

    List<Domain> findDomainsByContractor_id(int id);
    List<Domain> findDomainsByValidator_id(int id);
    @Query(
            value = "SELECT * FROM domains WHERE name LIKE CONCAT('%',?1,'%')",
            nativeQuery = true)
    List<Domain> findDomainsByNameIsLike(String string);
    List<Domain> findDomainsByName(String string);
}
