package jobportal.dao;


import jobportal.models.WorkType;
import org.springframework.data.repository.CrudRepository;

public interface WorktypeRepository extends CrudRepository <WorkType, Integer>
{

}