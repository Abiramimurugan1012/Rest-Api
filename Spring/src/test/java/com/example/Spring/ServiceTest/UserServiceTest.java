package com.example.Spring.ServiceTest;

import com.example.Spring.Model.User;
import com.example.Spring.Repo.UserRepo;
import com.example.Spring.Service.Userservice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    private Userservice userservice;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser(){
        User user=new User();
        List<User> userList= Arrays.asList(user);
        when(userRepo.findAll()).thenReturn(userList);
        List<User> response=userservice.getAllUser();
        Assertions.assertEquals(userList,response);
    }

    @Test
    void addUser(){
        User user=new User();
        when(userRepo.save(user)).thenReturn(user);
        User response=userservice.addUser(user);
        Assertions.assertEquals(user,response);
    }

    @Test
    void getUserById(){
        Long id=1L;
        User user=new User();
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        Object response=userservice.getUserById(id);
        Assertions.assertEquals(response,response);
    }

    @Test
    void getUserByIdNotFound(){
        Long id=1L;
        when(userRepo.findById(id)).thenReturn(Optional.empty());
        Object response=userservice.getUserById(id);
        Assertions.assertEquals("User not found",response);
    }

    @Test
    void updateUser(){
        Long id=1L;
        User user=new User();
        User user1=new User();
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user1);
        User response=userservice.updateUser(id,user);
        Assertions.assertEquals(user,response);
    }

    @Test
    void deleteUser() {
        Long id=1L;
        User user=new User();
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        String response=userservice.deleteUser(id);
        Assertions.assertEquals("Deleted Successfully!!!!!!",response);

    }

    @Test
    void deleteUserNotFound(){
        Long id=1L;
        when(userRepo.findById(id)).thenReturn(Optional.empty());
        String response=userservice.deleteUser(id);
        Assertions.assertEquals("Not found.......",response);
    }
}
