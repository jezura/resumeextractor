package firemni_system.dao;

import firemni_system.models.Validator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;


public interface ValidatorRepository extends CrudRepository <Validator, Integer> {

    Validator findValidatorByLogin(String login);


}