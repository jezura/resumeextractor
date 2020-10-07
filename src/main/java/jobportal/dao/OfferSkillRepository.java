package jobportal.dao;


import jobportal.models.OfferSkill;
import jobportal.models.Skill;
import org.springframework.data.repository.CrudRepository;

public interface OfferSkillRepository extends CrudRepository <OfferSkill, Integer>
{
    OfferSkill findOfferSkillById(String id);
}