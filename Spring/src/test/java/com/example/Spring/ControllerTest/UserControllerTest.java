package com.example.Spring.ControllerTest;

import com.example.Spring.Controller.Usercontroller;
import com.example.Spring.Model.User;
import com.example.Spring.Service.Userservice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private Usercontroller usercontroller;

    @Mock
    private Userservice userservice;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser(){
        User user=new User();
        user.setName("abi");
        List<User> userList = Arrays.asList(user);
        when(userservice.getAllUser()).thenReturn(userList);
        List<User> response=usercontroller.getUser();
        Assertions.assertEquals(userList,response);
    }

    @Test
    void addUser(){
        User user=new User();
        user.setName("");
        when(userservice.addUser(user)).thenReturn(user);
        User user1=usercontroller.adduser(user);
        Assertions.assertEquals(user,user1);
    }

    @Test
    void getUserById(){
        Long id=1L;
        User user=new User();
        when(userservice.getUserById(id)).thenReturn(Optional.of(user));
        Object response=usercontroller.getUserById(id);
        assertEquals(response,response);
    }


    @Test
    void updateUser() {
        Long id=1L;
        User user=new User();
        when(userservice.updateUser(id,user)).thenReturn(user);
        ResponseEntity<?> response=usercontroller.updateUser(id,user);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Successfully Updated!!!!!",response.getBody());
    }

    @Test
    void updateUserNotFound() {
        Long id=1L;
        User user=new User();
        when(userservice.updateUser(id,user)).thenThrow(new IllegalArgumentException());
        ResponseEntity<?> response=usercontroller.updateUser(id,user);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals("User not found....",response.getBody());
    }

    @Test
    void deleteUser(){
        Long id=1L;
        when(userservice.deleteUser(id)).thenReturn("");
        String response=usercontroller.deleteUser(id);
        assertEquals("",response);
    }
}
