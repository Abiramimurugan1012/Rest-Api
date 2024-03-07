package com.example.Spring.ServiceTest;

import com.example.Spring.Model.Login;
import com.example.Spring.Repo.LoginRepo;
import com.example.Spring.Service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;

public class LoginServiceTest {

    @InjectMocks
    LoginService loginService;

    @Mock
    LoginRepo loginRepo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signupForCorrectUser(){
        Login login=new Login();
        login.setUsername("Abi");
        login.setPassword("asdf");
        when(loginRepo.findByUsername("Abi")).thenReturn(null);
        String data=loginService.signup(login);
        Assertions.assertEquals(data,"Saved Successfully");
    }

    @Test
    public void signupForInvalidUser(){
        Login login=new Login();
        login.setUsername("asd");
        login.setPassword("asff");
        when(loginRepo.findByUsername("asd")).thenReturn(login);
        String data=loginService.signup(login);
        Assertions.assertEquals(data,"User name is already register");
    }

    @Test
    public void loginUserNotFound(){
        Login login=new Login();
        login.setUsername("asd");
        String hashedPassword = new BCryptPasswordEncoder().encode("asdf");
        login.setPassword(hashedPassword);
        when(loginRepo.findByUsername("asd")).thenReturn(null);
        Login data=new Login();
        data.setUsername("acv");
        data.setPassword("ascf");
        String response=loginService.login(data);
        Assertions.assertEquals(response,"User not found");
    }

    @Test
    public void loginSucessfull(){
        Login login=new Login();
        login.setUsername("asd");
        String passwordEncoder=new BCryptPasswordEncoder().encode("asdf");
        login.setPassword(passwordEncoder);
        when(loginRepo.findByUsername("asd")).thenReturn(login);
        Login data=new Login();
        data.setUsername("asd");
        data.setPassword("asdf");
        String response=loginService.login(data);
        Assertions.assertNotNull(response);

    }

    @Test
    public void loginForInvalidUsername(){
        Login login=new Login();
        login.setUsername("asd");
        String passwordEncoder=new BCryptPasswordEncoder().encode("asf");
        login.setPassword(passwordEncoder);
        when(loginRepo.findByUsername("asd")).thenReturn(login);
        Login data=new Login();
        data.setUsername("asd");
        data.setPassword("adbf");
        String response=loginService.login(data);
        Assertions.assertEquals(response,"Enter valid password");
    }

    @Test
    public void deleteUser(){
        Login login=new Login();
        login.setUsername("Abi");
        when(loginRepo.findByUsername("Abi")).thenReturn(login);
        String response=loginService.deleteuser("Abi");
        Assertions.assertEquals(response,"Deleted Successfully");
    }

    @Test
    public void deleteUserNotFound() {
        Login login=new Login();
        login.setUsername("asd");
        when(loginRepo.findByUsername("asd")).thenReturn(null);
        String response=loginService.deleteuser("");
        Assertions.assertEquals(response,"User not found");
    }

}
