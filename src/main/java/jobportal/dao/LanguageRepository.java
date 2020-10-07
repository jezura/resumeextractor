package jobportal.dao;


import jobportal.models.Language;
import jobportal.models.Skill;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository <Language, Integer>
{
    Language findLanguageById(String id);
}