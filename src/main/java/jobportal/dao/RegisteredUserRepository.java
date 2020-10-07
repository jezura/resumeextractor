package jobportal.dao;

import jobportal.models.RegisteredUser;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RegisteredUserRepository extends CrudRepository <RegisteredUser, Integer>
{

    RegisteredUser findRegisteredUserByLogin(String login);
    List<RegisteredUser> findRegisteredUsersByFirstNameLastName(String name);

}