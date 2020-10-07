package jobportal.dao;


import jobportal.models.Field;
import jobportal.models.Profession;
import org.springframework.data.repository.CrudRepository;

public interface ProfessionRepository extends CrudRepository <Profession, Integer>
{
    Profession findProfessionById(String id);
    Profession findProfessionByCode(String code);
}