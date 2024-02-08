package com.example.Spring.ControllerTest;

import com.example.Spring.Controller.AdminController;
import com.example.Spring.Model.Admin;
import com.example.Spring.Repo.AdminRepo;
import com.example.Spring.Service.AdminService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@MockitoSettings
public class AdminControllerTest {
    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;
    private AdminRepo adminRepo;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmployeeTest(){
        Admin admin = new Admin();
        admin.setEmail("mail");
        List<Admin> adminList = Arrays.asList(admin);
        when(adminService.getAllEmployee()).thenReturn(adminList);

        List<Admin> response = adminController.getAll();
        assertEquals(adminList,response);
    }

    @Test
    void addEmployee(){
        Admin admin=new Admin();
        admin.setFname("frame");
        when(adminService.addEmployee(admin)).thenReturn(admin);
        Admin admin1=adminController.addDetail(admin);
        assertEquals(admin,admin1);

    }

    @Test
    void getEmployeeById(){
        long id = 1L;
        Admin admin = new Admin(id, "","","");
        when(adminService.getEmployeeById(id)).thenReturn(Optional.of(admin));
        ResponseEntity<?> response = adminController.getId(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin,((Optional<Admin>) response.getBody()).get());
    }

    @Test
    void getEmployeeNotFound(){
        long id=1L;
        when(adminService.getEmployeeById(id)).thenReturn(Optional.empty());
        ResponseEntity<?> response = adminController.getId(id);
        verify(adminService, times(1)).getEmployeeById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Employee not found.....",response.getBody());
    }

    @Test
    void updateEmployee(){
        long id=1L;
        Admin admin=new Admin();
        admin.setId(id);
        when(adminService.updateEmployee(id,admin)).thenReturn(admin);
        ResponseEntity<?> response=adminController.updateDetail(id,admin);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Successfully updated!!!",response.getBody());
    }

    @Test
    void updateEmployeeNotFound(){
        long id=1L;
        Admin admin=new Admin();
        admin.setId(id);
        when(adminService.updateEmployee(id,admin)).thenThrow(new IllegalArgumentException());
        ResponseEntity<?> response=adminController.updateDetail(id,admin);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals("Employee not found......",response.getBody());
    }

    @Test
    void deleteEmployee(){
        long id=1L;
        Admin admin=new Admin();
        admin.setId(id);
        when(adminService.deleteEmployee(id)).thenReturn("");
        String response=adminController.deleteEmp(id);
        Assertions.assertEquals("",response);


    }


}
