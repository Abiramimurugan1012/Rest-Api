package com.example.Spring.Service;

import com.example.Spring.Model.User;
import com.example.Spring.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

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



        public Object getUserById(Long id) {
        Optional<User> user=userRepo.findById(id);
        if (user.isPresent()){
            return user;
        }
        else {
            return "User not found";
        }

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

    public User updatePhno(Long id,String phno){
        Optional<User> user=userRepo.findById(id);
        if(user.isPresent()){
            User user1=user.get();
            user1.setPhone_no(phno);
            return userRepo.save(user1);
        }
        else {
            throw new IllegalArgumentException("User not found");
        }
    }
}

