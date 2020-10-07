package jobportal.dao;


import jobportal.models.Village;
import jobportal.models.VillagePart;
import org.springframework.data.repository.CrudRepository;

public interface VillagePartRepository extends CrudRepository <VillagePart, Integer>
{
    VillagePart findVillagePartById(String id);
    VillagePart findVillagePartByCode(String code);
}