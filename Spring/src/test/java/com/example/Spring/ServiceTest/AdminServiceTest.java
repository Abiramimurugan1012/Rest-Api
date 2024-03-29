package com.example.Spring.ServiceTest;

import com.example.Spring.Model.Admin;
import com.example.Spring.Repo.AdminRepo;
import com.example.Spring.Service.AdminService;
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

public class AdminServiceTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepo adminRepo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser(){
        Admin admin=new Admin();
        admin.setEmail("");
        List<Admin> adminList = Arrays.asList(admin);
        when(adminRepo.findAll()).thenReturn(adminList);
        List<Admin> admin1=adminService.getAllEmployee();
        assertEquals(adminList,admin1);

    }

    @Test
    void addUser(){
        Admin admin=new Admin();
        when(adminRepo.save(admin)).thenReturn(admin);
        Admin admin1=adminService.addEmployee(admin);
        assertEquals(admin,admin1);
    }

    @Test
    void getUserById(){
        Long id=1L;
        Admin admin=new Admin();
        when(adminRepo.findById(id)).thenReturn(Optional.of(admin));
        Object response=adminService.getEmployeeById(id);
        assertEquals(response,response);
    }

    @Test
    void getUserByIdNotfound(){
        Long id=1L;
        when(adminRepo.findById(id)).thenReturn(Optional.empty());
        Object response=adminService.getEmployeeById(id);
        assertEquals("Employee not found......",response);
    }

    @Test
    void updateUserById(){
        Long id=1L;
        Admin admin=new Admin();
        Admin admin1=new Admin();
        when(adminRepo.findById(id)).thenReturn(Optional.of(admin));
        when(adminRepo.save(admin)).thenReturn(admin1);
        Admin response= adminService.updateEmployee(id, admin);
        assertEquals(admin1,response);

    }

    @Test
    void deleteUser(){
        Long id=1L;
        Admin admin=new Admin();
        when(adminRepo.findById(id)).thenReturn(Optional.of(admin));
        String response=adminService.deleteEmployee(id);
        assertEquals("Deleted Successfully!!!!!!!",response);
    }

    @Test
    void deleteUserNotFound(){
        Long id=1L;
        when(adminRepo.findById(id)).thenReturn(Optional.empty());
        String response=adminService.deleteEmployee(id);
        assertEquals("Not found.....",response);
    }

    @Test
    void editEmail(){
        Long id=1L;
        String email="abc";
        Admin admin=new Admin();
        when(adminRepo.findById(id)).thenReturn(Optional.of(admin));
        when(adminRepo.save(admin)).thenReturn(admin);
        Admin response=adminService.updateEmail(id,email);
        assertEquals(response,admin);

    }

    @Test
    void emailNotFound(){
        Long id=1L;
        String email="abc";
        when(adminRepo.findById(id)).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            adminService.updateEmail(id, email);
        });
        assertEquals("User not found", exception.getMessage());
    }

}
