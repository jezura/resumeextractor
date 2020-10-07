package jobportal.dao;


import jobportal.models.PlaceType;
import jobportal.models.Skill;
import org.springframework.data.repository.CrudRepository;

public interface PlaceTypeRepository extends CrudRepository <PlaceType, Integer>
{
    PlaceType findPlaceTypeById(String id);
}