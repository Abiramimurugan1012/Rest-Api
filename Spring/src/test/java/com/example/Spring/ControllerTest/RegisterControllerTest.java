package com.example.Spring.ControllerTest;

import com.example.Spring.Controller.RegisterController;
import com.example.Spring.Model.Register;
import com.example.Spring.Repo.RegisterRepo;
import com.example.Spring.Service.RegisterService;
import io.jsonwebtoken.Claims;
import org.apache.el.stream.Optional;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterControllerTest {

    @InjectMocks
    RegisterController registerController;

    @Mock
    RegisterService registerService;

    @Mock
    RegisterRepo registerRepo;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerUser(){
        Register register=new Register();
        when(registerService.register(register)).thenReturn("");
        String reg=registerController.register(register);
        assertEquals(reg,"");
    }

    @Test
    public void loginUser(){
        Register register=new Register();
        when(registerService.login(register)).thenReturn("");
        String reg=registerController.login(register);
        assertEquals(reg,"");
    }

    @Test
    public void updateUserSuccessfully(){
        Long id=1L;
        Register register=new Register();
        when(registerService.update(id,register)).thenReturn(null);
        String reg=registerController.update(id,register);
        assertEquals(reg,"Updated Successfully!!");
    }

    @Test
    public void updateUserNotFound(){
        Long id=1L;
        Register register=new Register();
        when(registerService.update(id,register)).thenThrow(new IllegalArgumentException());
        String reg=registerController.update(id,register);
        assertEquals(reg,"User not Found");
    }

    @Test
    public void deleteUser(){
        Long id=1L;
        when(registerService.delete(id)).thenReturn("");
        String reg=registerController.delete(id);
        assertEquals(reg,"");
    }

    @Test
    public void getAllUser(){
        List<Register> register= Arrays.asList();
        when(registerService.getUser()).thenReturn(register);
        List<Register> reg=registerController.get();
        assertEquals(reg,register);

    }

    @Test
    public void getUserByToken(){
        String token = "valid_token";
        Claims claims = mock(Claims.class);
        when(registerService.decodeToken(token)).thenReturn(claims);
        String username = "testUser";
        when(claims.getSubject()).thenReturn(username);
        Register user = new Register();
        when(registerRepo.findByUsername(username)).thenReturn(user);
        ResponseEntity<?> responseEntity = registerController.getDetailsUsingToken("Bearer " + token);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());

    }

    @Test
    public void invalidToken(){
        String token = "invalid_token";
        when(registerService.decodeToken(token)).thenReturn(null);
        ResponseEntity<?> responseEntity = registerController.getDetailsUsingToken("Bearer " + token);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Invalid or expired token", responseEntity.getBody());
    }
    @Test
    public void getUserByTokenInvalid() {
        String token = "valid_token";
        Claims claims = mock(Claims.class);
        when(registerService.decodeToken(token)).thenReturn(claims);
        when(claims.getSubject()).thenReturn(null);
        ResponseEntity<?> responseEntity = registerController.getDetailsUsingToken("Bearer " + token);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid token: missing subject", responseEntity.getBody());
    }
    @Test
    public void getUserByTokenUserNotFound() {
        String token = "valid_token";
        Claims claims = mock(Claims.class);
        when(registerService.decodeToken(token)).thenReturn(claims);
        String username = "testUser";
        when(claims.getSubject()).thenReturn(username);
        when(registerRepo.findByUsername(username)).thenReturn(null);
        ResponseEntity<?> responseEntity = registerController.getDetailsUsingToken("Bearer " + token);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User not found", responseEntity.getBody());
    }




}
