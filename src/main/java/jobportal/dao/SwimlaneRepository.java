package jobportal.dao;


import jobportal.models.SwimlaneType;
import org.springframework.data.repository.CrudRepository;


public interface SwimlaneRepository extends CrudRepository<SwimlaneType, Integer> {
    
}
