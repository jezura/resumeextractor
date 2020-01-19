package firemni_system.dao;

import firemni_system.models.Contractor;

import firemni_system.models.Validator;
import org.springframework.data.repository.CrudRepository;


public interface ContractorRepository  extends CrudRepository <Contractor, Integer>
{
    Contractor findContractorByLogin(String login);

}