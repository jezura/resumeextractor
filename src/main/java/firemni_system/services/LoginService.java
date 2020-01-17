package firemni_system.services;

import firemni_system.dao.LoginRepository;
import firemni_system.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public Collection<Login> findAllLogins(){
        List<Login> logins = new ArrayList<Login>();
        for (Login login :loginRepository.findAll())
        {
            logins.add(login);
        }
        return logins;
    }

    public void deleteLogin(int id){
        loginRepository.deleteById(id);
    }

    public void saveLogin(Login l){
        loginRepository.save(l);
    }

    public Login getLogin(int id){
        return loginRepository.findById(id).get();
    }


}