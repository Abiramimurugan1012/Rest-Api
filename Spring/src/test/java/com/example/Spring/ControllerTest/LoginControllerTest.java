package com.example.Spring.ControllerTest;

import com.example.Spring.Controller.LoginController;
import com.example.Spring.Model.Login;
import com.example.Spring.Service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @InjectMocks
    LoginController loginController;

    @Mock
    LoginService loginService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signup(){
        Login login=new Login();
        when(loginService.signup(login)).thenReturn("its done");
        String response=loginController.signup(login);
        Assertions.assertEquals(response,"its done");
    }

    @Test
    public void login(){
        Login login=new Login();
        when(loginService.login(login)).thenReturn("");
        String response=loginController.login(login);
        Assertions.assertEquals(response,"");
    }

}
