package jobportal.dao;


import jobportal.models.District;
import jobportal.models.Skill;
import jobportal.models.Village;
import org.springframework.data.repository.CrudRepository;

public interface SkillRepository extends CrudRepository <Skill, Integer>
{
    Skill findSkillById(String id);
}