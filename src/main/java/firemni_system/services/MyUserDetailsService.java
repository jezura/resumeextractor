package firemni_system.services;

import firemni_system.models.Contractor;
import firemni_system.models.Manager;
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
            return new  MyUser(optionalValidator.get().getLogin(), optionalValidator.get().getPassword(), optionalValidator.get().getRole(), optionalValidator.get().getId());
        }
        Optional <Contractor> optionalContractor = Optional.ofNullable(personService.findContractorByLogin(userName));
        if(optionalContractor.isPresent()) {
            return new  MyUser(optionalContractor.get().getLogin(), optionalContractor.get().getPassword(), optionalContractor.get().getRole() ,optionalContractor.get().getId());
        }
        Optional <Manager> optionalManager = Optional.ofNullable(personService.findManagerByLogin(userName));
        if(optionalManager.isPresent()) {
            return new  MyUser(optionalManager.get().getLogin(), optionalManager.get().getPassword(), optionalManager.get().getRole() ,optionalManager.get().getId());
        }
        return null;
    }
}
