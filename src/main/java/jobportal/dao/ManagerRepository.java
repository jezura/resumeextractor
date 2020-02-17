package jobportal.dao;

import jobportal.models.Manager;

import org.springframework.data.repository.CrudRepository;


public interface ManagerRepository extends CrudRepository <Manager, Integer>
{

    Manager findManagerByLogin(String login);

}