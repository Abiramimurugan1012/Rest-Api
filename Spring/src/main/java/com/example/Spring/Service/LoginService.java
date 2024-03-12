package com.example.Spring.Service;

import com.example.Spring.Model.Login;
import com.example.Spring.Model.Register;
import com.example.Spring.Repo.LoginRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    LoginRepo loginRepo;

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

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
                String token = generateToken(data.getUsername());
                return token;
            } else {
                return "Enter valid password";
            }
        }
            else {
                return "User not found";
            }
    }
        public String generateToken(String username) {
            String base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            return Jwts.builder()
                    .setSubject(username)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();

}
    public String deleteuser(String username){
        Login get=loginRepo.findByUsername(username);
        if(get!=null){
            loginRepo.delete(get);
            return "Deleted Successfully";
        }
        else {
            return "User not found";
        }
    }

    public List<Login> getAll(){
        return loginRepo.findAll();
    }


}
