package resumeextractor.services;

import resumeextractor.dao.RegisteredUserRepository;
import resumeextractor.dao.AdministratorRepository;
import resumeextractor.models.Administrator;
import resumeextractor.models.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class PersonService {
    @Autowired
    private RegisteredUserRepository registeredUserRepository;
    @Autowired
    private AdministratorRepository administratorRepository;

    public Collection<RegisteredUser> findAllRegisteredUsers(){
        List<RegisteredUser> registeredUsers = new ArrayList<RegisteredUser>();
        for (RegisteredUser registeredUser :registeredUserRepository.findAll())
        {
            registeredUsers.add(registeredUser);
        }
        return registeredUsers;
    }

    public Collection<RegisteredUser> findRegisteredUsersByFirstNameLastName(String name){
        List<RegisteredUser> registeredUsers = new ArrayList<RegisteredUser>();
        for (RegisteredUser registeredUser :registeredUserRepository.findRegisteredUsersByFirstNameLastName(name))
        {
            registeredUsers.add(registeredUser);
        }
        return registeredUsers;
    }


    public RegisteredUser findRegisteredUserByLogin(String login){
        RegisteredUser registeredUser = registeredUserRepository.findRegisteredUserByLogin(login);
        return registeredUser;
    }

    public Administrator findAdministratorByLogin(String login){
        Administrator administrator = administratorRepository.findAdministratorByLogin(login);
        return administrator;
    }

    public boolean isUnique(String login){
        boolean unique = true;

        if(findRegisteredUserByLogin(login) != null)
            unique = false;

        if(findAdministratorByLogin(login) != null)
            unique = false;

        return unique;
    }

    public void deleteRegisteredUser(int id){
        registeredUserRepository.deleteById(id);
    }

    public void saveRegisteredUser(RegisteredUser ru){
        registeredUserRepository.save(ru);
    }

    public RegisteredUser getRegisteredUser(int id){
        return registeredUserRepository.findById(id).get();
    }
}