package com.example.Spring.Service;

import com.example.Spring.Model.User;
import com.example.Spring.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class Userservice {
    @Autowired
    private UserRepo userRepo;

    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    public User addUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found")));


    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = Optional.ofNullable(userRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("User not found")));
            User user = optionalUser.get();
            user.setName(userDetails.getName());
            user.setAddress(userDetails.getAddress());
            user.setPhone_no(userDetails.getPhone_no());
            return userRepo.save(user);

    }

    public String  deleteUser(Long id){
        if(userRepo.findById(id).isPresent()) {
            userRepo.deleteById(id);
            return "Deleted Successfully!!!!!!";
        }
        else {
            return "Not found.......";
        }
    }
}
