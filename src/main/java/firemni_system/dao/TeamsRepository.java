package firemni_system.dao;

import firemni_system.models.Team;
import firemni_system.models.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TeamsRepository extends CrudRepository <Team, Integer>
{
}