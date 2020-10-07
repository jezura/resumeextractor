package jobportal.dao;


import jobportal.models.District;
import jobportal.models.Employer;
import org.springframework.data.repository.CrudRepository;

public interface EmployerRepository extends CrudRepository <Employer, Integer>
{
    Employer findEmployerByIco(int ico);
}