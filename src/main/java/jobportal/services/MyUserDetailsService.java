package jobportal.services;


import jobportal.models.Administrator;
import jobportal.models.RegisteredUser;
import jobportal.security.MyUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    PersonService personService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional <RegisteredUser> optionalRegisteredUser = Optional.ofNullable(personService.findRegisteredUserByLogin(userName));
        if(optionalRegisteredUser.isPresent()) {
            return new  MyUser(optionalRegisteredUser.get().getLogin(), optionalRegisteredUser.get().getPassword(), optionalRegisteredUser.get().getRole() ,optionalRegisteredUser.get().getId());
        }
        Optional <Administrator> optionalAdministrator = Optional.ofNullable(personService.findAdministratorByLogin(userName));
        if(optionalAdministrator.isPresent()) {
            return new  MyUser(optionalAdministrator.get().getLogin(), optionalAdministrator.get().getPassword(), optionalAdministrator.get().getRole() ,optionalAdministrator.get().getId());
        }
        return null;
    }
}