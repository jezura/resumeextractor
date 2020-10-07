package jobportal.dao;


import jobportal.models.Skill;
import jobportal.models.Workship;
import org.springframework.data.repository.CrudRepository;

public interface WorkshipRepository extends CrudRepository <Workship, Integer>
{
    Workship findWorkshipById(String id);
    Workship findWorkshipByCode(String code);
}