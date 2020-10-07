package jobportal.dao;


import jobportal.models.District;
import jobportal.models.Region;
import org.springframework.data.repository.CrudRepository;

public interface DistrictRepository extends CrudRepository <District, Integer>
{
    District findDistrictById(String id);
}