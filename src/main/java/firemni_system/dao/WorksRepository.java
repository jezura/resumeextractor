package firemni_system.dao;

import firemni_system.models.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface WorksRepository extends CrudRepository <Work, Integer>
{
    List<Work> findWorksByContractor_Id(int id);
}