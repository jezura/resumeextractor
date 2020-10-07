package jobportal.dao;


import jobportal.models.WorkPlace;
import jobportal.models.Workshift;
import org.springframework.data.repository.CrudRepository;

public interface WorkPlaceRepository extends CrudRepository <WorkPlace, Integer>
{
    WorkPlace findWorkPlaceById(String id);
}