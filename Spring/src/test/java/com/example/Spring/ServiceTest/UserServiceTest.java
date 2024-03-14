package com.example.Spring.ServiceTest;

import com.example.Spring.Model.User;
import com.example.Spring.Repo.UserRepo;
import com.example.Spring.Service.Userservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertEquals(userList,response);
    }

    @Test
    void addUser(){
        User user=new User();
        when(userRepo.save(user)).thenReturn(user);
        User response=userservice.addUser(user);
        assertEquals(user,response);
    }

    @Test
    void getUserById(){
        Long id=1L;
        User user=new User();
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        Object response=userservice.getUserById(id);
        assertEquals(response,response);
    }

    @Test
    void getUserByIdNotFound(){
        Long id=1L;
        when(userRepo.findById(id)).thenReturn(Optional.empty());
        Object response=userservice.getUserById(id);
        assertEquals("User not found",response);
    }

    @Test
    void updateUser(){
        Long id=1L;
        User user=new User();
        User user1=new User();
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user1);
        User response=userservice.updateUser(id,user);
        assertEquals(user,response);
    }

    @Test
    void deleteUser() {
        Long id=1L;
        User user=new User();
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        String response=userservice.deleteUser(id);
        assertEquals("Deleted Successfully!!!!!!",response);

    }

    @Test
    void deleteUserNotFound(){
        Long id=1L;
        when(userRepo.findById(id)).thenReturn(Optional.empty());
        String response=userservice.deleteUser(id);
        assertEquals("Not found.......",response);
    }

    @Test
    void updatePhno(){
        Long id=1L;
        int phno=111113;
        User user=new User();
        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user);
        User response=userservice.updatePhno(id,phno);
        assertEquals(response,user);
    }

    @Test
    void  phnoNotFound(){
        Long id=1L;
        int phno=12334;
        when(userRepo.findById(id)).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userservice.updatePhno(id,phno);
        });
        assertEquals("User not found", exception.getMessage());
    }
}
