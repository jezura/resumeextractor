package jobportal.dao;

import jobportal.models.Contractor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContractorRepository  extends CrudRepository <Contractor, Integer>
{
    Contractor findContractorByLogin(String login);

    List<Contractor> findContractorsByMentor_Id(int id);

    @Query(
            value = "SELECT * FROM contractors WHERE CONCAT(first_name, ' ', last_name) LIKE CONCAT('%',?1,'%')",
            nativeQuery = true)
    List<Contractor> findContractorsByName(String name);

}