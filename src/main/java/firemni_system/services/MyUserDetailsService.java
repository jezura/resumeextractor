package firemni_system.services;

import firemni_system.models.Validator;
import firemni_system.security.MyUser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    PersonService personService;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
      Optional <Validator> optionalValidator = Optional.ofNullable(personService.findValidatorByLogin(userName));

        if(optionalValidator.isPresent()) {
            return new  MyUser(optionalValidator.get().getLogin(), optionalValidator.get().getPassword());
        } else {
            return null;
        }
    }
}
