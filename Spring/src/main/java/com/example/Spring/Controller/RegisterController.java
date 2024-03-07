package com.example.Spring.Controller;


import com.example.Spring.Model.Login;
import com.example.Spring.Model.Register;
import com.example.Spring.Repo.RegisterRepo;
import com.example.Spring.Service.RegisterService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    RegisterService registerService;


    @Autowired
    RegisterRepo registerRepo;

    @PostMapping("/user")
    public String register(@RequestBody Register register){
        return registerService.register(register);
    }

    @PostMapping("/login")
    public String login(@RequestBody Register register){
        return registerService.login(register);
    }

    @PutMapping("/edit/{id}")
    public String update(@PathVariable Long id,@RequestBody Register register){
        try {
            registerService.update(id,register);
            return "Updated Successfully!!";
        }
        catch (IllegalArgumentException e){
            return "User not Found";
        }
    }

    @DeleteMapping("/del/{id}")
    public String delete(@PathVariable Long id){
        return registerService.delete(id);
    }

    @GetMapping("/get")
    public List<Register> get(){
        return registerService.getUser();
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getDetailsUsingToken(@RequestHeader("Authorization") String token){
        token = token.substring(7);
        Claims claims = registerService.decodeToken(token);
        if (claims == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        } else {
            String username = claims.getSubject();
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token: missing subject");
            }
            Register user = registerRepo.findByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.ok(user);
        }
    }






}
