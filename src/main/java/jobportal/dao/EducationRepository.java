package jobportal.dao;


import jobportal.models.Education;
import jobportal.models.Skill;
import org.springframework.data.repository.CrudRepository;

public interface EducationRepository extends CrudRepository <Education, Integer>
{
    Education findEducationById(String id);
    Education findEducationByCode(String code);
}