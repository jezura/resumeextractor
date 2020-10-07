package jobportal.dao;


import jobportal.models.Suitability;
import jobportal.models.Workship;
import org.springframework.data.repository.CrudRepository;

public interface SuitabilityRepository extends CrudRepository <Suitability, Integer>
{
    Suitability findSuitabilityById(String id);
    Suitability findSuitabilityByCode(String code);
}