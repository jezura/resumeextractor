package firemni_system.dao;


import firemni_system.models.Domain;
import firemni_system.models.SwimlaneType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SwimlaneRepository extends CrudRepository<SwimlaneType, Integer> {
    
}
