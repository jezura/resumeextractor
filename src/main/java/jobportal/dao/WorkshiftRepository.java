package jobportal.dao;


import jobportal.models.Workshift;
import jobportal.models.Workship;
import org.springframework.data.repository.CrudRepository;

public interface WorkshiftRepository extends CrudRepository <Workshift, Integer>
{
    Workshift findWorkshiftById(String id);
    Workshift findWorkshiftByCode(String code);
}