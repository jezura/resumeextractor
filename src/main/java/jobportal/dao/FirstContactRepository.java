package jobportal.dao;


import jobportal.models.Employer;
import jobportal.models.FirstContact;
import org.springframework.data.repository.CrudRepository;

public interface FirstContactRepository extends CrudRepository <FirstContact, Integer>
{
    FirstContact findFirstContactById(int id);
}