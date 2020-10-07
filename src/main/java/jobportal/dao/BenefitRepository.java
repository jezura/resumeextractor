package jobportal.dao;


import jobportal.models.Benefit;
import jobportal.models.Skill;
import org.springframework.data.repository.CrudRepository;

public interface BenefitRepository extends CrudRepository <Benefit, Integer>
{
    Benefit findBenefitById(String id);
}