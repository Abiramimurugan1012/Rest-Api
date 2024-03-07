package com.example.Spring.Service;


import com.example.Spring.Model.Register;
import com.example.Spring.Repo.RegisterRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.awt.desktop.ScreenSleepEvent;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RegisterService {
    @Autowired
    RegisterRepo registerRepo;

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);



    public String register(Register register){
        Register test=registerRepo.findByUsername(register.getUsername());
        if (test==null) {
            Register info = new Register();
            info.setUsername(register.getUsername());
            info.setAge(register.getAge());
            info.setAddress(register.getAddress());
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            info.setPassword(bCryptPasswordEncoder.encode(register.getPassword()));
            registerRepo.save(info);
            return "Saved Successfully";
        }
        else {
            return "User name is already register";
        }
    }

    public String login(Register data){
        Register get=registerRepo.findByUsername(data.getUsername());
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

    public String  generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+36000000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }
    public Claims decodeToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Register update(Long id,Register register){
        Optional<Register> details=Optional.ofNullable(registerRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("User not found")));
        Register register1=details.get();
        register1.setUsername(register.getUsername());
        register1.setAge(register.getAge());
        register1.setAddress(register.getAddress());
        return registerRepo.save(register1);
    }

    public String delete(Long id){
        if(registerRepo.findById(id).isPresent()) {
            registerRepo.deleteById(id);
            return "Deleted Successfully";
        }
        else {
            return "User not Found";
        }

    }

    public List<Register> getUser(){
        return registerRepo.findAll();
    }

}
