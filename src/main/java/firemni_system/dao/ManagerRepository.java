package firemni_system.dao;

import firemni_system.models.Manager;

import org.springframework.data.repository.CrudRepository;


public interface ManagerRepository extends CrudRepository <Manager, Integer>
{

    Manager findManagerByLogin(String login);

}