package jobportal.dao;

import jobportal.models.Administrator;
import org.springframework.data.repository.CrudRepository;


public interface AdministratorRepository extends CrudRepository <Administrator, Integer>
{
    Administrator findAdministratorByLogin(String login);
}