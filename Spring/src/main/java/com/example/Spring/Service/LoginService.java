package com.example.Spring.Service;

import com.example.Spring.Model.Login;
import com.example.Spring.Repo.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginRepo loginRepo;

    public String signup(Login data){
        Login test=loginRepo.findByUsername(data.getUsername());
        if (test==null) {
            Login info = new Login();
            info.setUsername(data.getUsername());
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            info.setPassword(bCryptPasswordEncoder.encode(data.getPassword()));
            loginRepo.save(info);
            return "Saved Successfully";
        }
        else {
            return "User name is already register";
        }
    }

    public String login(Login data){
        Login get=loginRepo.findByUsername(data.getUsername());
        if(get!=null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(data.getPassword(), get.getPassword())) {
                return "Login Successfully";
            } else {
                return "Enter valid Username and password";
            }
        }
            else {
                return "User not found";
            }
    }


}
