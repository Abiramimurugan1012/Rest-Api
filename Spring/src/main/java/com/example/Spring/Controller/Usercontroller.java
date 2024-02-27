package com.example.Spring.Controller;

import com.example.Spring.Model.User;
import com.example.Spring.Service.Userservice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Usercontroller {
    @Autowired
    private Userservice userservice;

    @GetMapping
    public List<User> getUser() {
        return userservice.getAllUser();
    }

    @PostMapping("/id")
    public User adduser(@RequestBody User user){
        return userservice.addUser(user);
    }

    @GetMapping("/{id}")
    public Object getUserById(@PathVariable Long id){
           return userservice.getUserById(id);  
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            userservice.updateUser(id,userDetails);
            return new ResponseEntity<>("Successfully Updated!!!!!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("User not found....",HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/del/{id}")
    public String deleteUser(@PathVariable Long id){
        return  userservice.deleteUser(id);
    }

}
