package jobportal.dao;

import jobportal.models.Team;
import org.springframework.data.repository.CrudRepository;


public interface TeamsRepository extends CrudRepository <Team, Integer>
{
}