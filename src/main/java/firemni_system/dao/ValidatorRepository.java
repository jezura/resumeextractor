package firemni_system.dao;

import firemni_system.models.Validator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;


public interface ValidatorRepository extends CrudRepository <Validator, Integer> {

    Validator findValidatorByLogin(String login);

    @Query(
            value = "SELECT * FROM validators WHERE CONCAT(first_name, ' ', last_name) LIKE CONCAT('%',?1,'%')",
            nativeQuery = true)
    List<Validator> findValidatorsByName(String name);

}