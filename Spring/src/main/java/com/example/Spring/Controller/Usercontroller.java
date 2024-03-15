package com.example.Spring.Controller;

import com.example.Spring.Model.User;
import com.example.Spring.Service.Userservice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        if (!isValidPhoneNumber(user.getPhone_no())) {
            String message = "Invalid phone number format. Please enter 10 digits.";
            return ResponseEntity.badRequest().body(message);
        }

        User savedUser = userservice.addUser(user);
        return ResponseEntity.ok(savedUser);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
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


    @PatchMapping("/patch/{id}")
    public String editPhone(@PathVariable Long id,@RequestBody User user){
        try {
            String phno= user.getPhone_no();
            userservice.updatePhno(id,phno);
            return "Phone number changed";
        }
        catch (IllegalArgumentException e){
            return "Not Found";
        }

    }


}
