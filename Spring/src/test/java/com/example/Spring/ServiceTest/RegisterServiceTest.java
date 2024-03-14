package com.example.Spring.ServiceTest;

import com.example.Spring.Model.Register;
import com.example.Spring.Repo.RegisterRepo;
import com.example.Spring.Service.RegisterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RegisterServiceTest {

    @InjectMocks
    RegisterService registerService;

    @Mock
    RegisterRepo registerRepo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void registerSuccess(){
        Register register=new Register();
        register.setUsername("Abi");
        register.setPassword("123");
        register.setAge(12);
        register.setAddress("cbe");
        when(registerRepo.findByUsername("Abi")).thenReturn(null);
        String reg=registerService.register(register);
        assertEquals(reg,"Saved Successfully");
    }

    @Test
    public void alreadyRegister(){
        Register register=new Register();
        register.setUsername("Abi");
        register.setPassword("123");
        register.setAge(12);
        register.setAddress("cbe");
        when(registerRepo.findByUsername("Abi")).thenReturn(register);
        String reg=registerService.register(register);
        assertEquals(reg,"User name is already register");
    }

    @Test
    public void userNotFound(){
        Register register=new Register();
        register.setUsername("Abi");
        String pwd=new BCryptPasswordEncoder().encode("asdf");
        register.setPassword(pwd);
        register.setAge(12);
        register.setAddress("cbe");
        when(registerRepo.findByUsername("Abi")).thenReturn(null);
        Register reg=new Register();
        reg.setUsername("Abi");
        reg.setPassword("123");
        reg.setAge(12);
        reg.setAddress("cbe");
        String register1=registerService.login(reg);
        assertEquals(register1,"User not found");
    }
    @Test
    public void token(){
        Register register=new Register();
        register.setUsername("Abi");
        String pwd=new BCryptPasswordEncoder().encode("123");
        register.setPassword(pwd);
        register.setAge(12);
        register.setAddress("cbe");
        when(registerRepo.findByUsername("Abi")).thenReturn(register);
        Register reg=new Register();
        reg.setUsername("Abi");
        reg.setPassword("123");
        reg.setAge(12);
        reg.setAddress("cbe");
        String register1=registerService.login(reg);
        Assertions.assertNotNull(register1);
    }

    @Test
    public void validPassword(){
        Register register=new Register();
        register.setUsername("Abi");
        String pwd=new BCryptPasswordEncoder().encode("123");
        register.setPassword(pwd);
        register.setAge(12);
        register.setAddress("cbe");
        when(registerRepo.findByUsername("Abi")).thenReturn(register);
        Register reg=new Register();
        reg.setUsername("Abi");
        reg.setPassword("345");
        String register1=registerService.login(reg);
        assertEquals(register1,"Enter valid password");

    }

    @Test
    public void updateUser(){
        Long id=1L;
        Register register=new Register();
        Register register1=new Register();
        when(registerRepo.findById(id)).thenReturn(Optional.of(register));
        when(registerRepo.save(register)).thenReturn(register1);
        Register reg=registerService.update(id,register);
        assertEquals(reg,register);
    }

    @Test
    public void deleteUser(){
        Long id=1L;
        Register register=new Register();
        when(registerRepo.findById(id)).thenReturn(Optional.of(register));
        String reg=registerService.delete(id);
        assertEquals(reg,"Deleted Successfully");

    }

    @Test
    public void deleteUserNotFound(){
        Long id=1L;
        when(registerRepo.findById(id)).thenReturn(Optional.empty());
        String reg=registerService.delete(id);
        assertEquals(reg,"User not Found");
    }

    @Test
    public void getAllUser(){
        List<Register> register= Arrays.asList();
        when(registerRepo.findAll()).thenReturn(register);
        List<Register> reg=registerService.getUser();
        assertEquals(reg,register);
    }

    @Test
    public void editAge(){
        Long id=1L;
        int age=10;
        Register register=new Register();
        when(registerRepo.findById(id)).thenReturn(Optional.of(register));
        when(registerRepo.save(register)).thenReturn(register);
        Register response=registerService.updateAge(id,age);
        assertEquals(response,register);
    }

    @Test
    public void ageNotFound(){
        Long id=1L;
        int age=10;
        when(registerRepo.findById(id)).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registerService.updateAge(id,age);
        });
        assertEquals("User not found", exception.getMessage());
    }



}
