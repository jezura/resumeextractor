package resumeextractor.dao;

import resumeextractor.models.RegisteredUser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RegisteredUserRepository extends CrudRepository <RegisteredUser, Integer>
{

    RegisteredUser findRegisteredUserByLogin(String login);

    @Query(
            value = "SELECT * FROM registered_users WHERE CONCAT(first_name, ' ', last_name) LIKE CONCAT('%',?1,'%')",
            nativeQuery = true)
    List<RegisteredUser> findRegisteredUsersByFirstNameLastName(String name);

}