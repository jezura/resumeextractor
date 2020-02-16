package firemni_system.dao;


import firemni_system.models.Region;
import org.springframework.data.repository.CrudRepository;


public interface SwimlaneRepository extends CrudRepository<Region, Integer> {
    
}
