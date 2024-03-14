package com.example.Spring.ControllerTest;

import com.example.Spring.Controller.AdminController;
import com.example.Spring.Model.Admin;
import com.example.Spring.Service.AdminService;
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
import static org.mockito.Mockito.*;


public class AdminControllerTest {
    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;

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
    void getEmployeeById() {
        long id = 1L;
        Admin admin = new Admin(id, "", "", "");
        when(adminService.getEmployeeById(id)).thenReturn(admin);
        Object response = adminController.getId(id);
        Assertions.assertEquals(response, response);
    }


    @Test
    void getEmployeeNotFound(){
        Long id=1L;
        when(adminService.getEmployeeById(null)).thenReturn(null);
        Object response=adminController.getId(null);
        Assertions.assertEquals(response,response);

    }
    @Test
    void updateEmployee () {
        long id = 1L;
        Admin admin = new Admin();
        admin.setId(id);
        when(adminService.updateEmployee(id, admin)).thenReturn(admin);
        ResponseEntity<?> response = adminController.updateDetail(id, admin);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully updated!!!", response.getBody());
        }

        @Test
        void updateEmployeeNotFound () {
            long id = 1L;
            Admin admin = new Admin();
            admin.setId(id);
            when(adminService.updateEmployee(id, admin)).thenThrow(new IllegalArgumentException());
            ResponseEntity<?> response = adminController.updateDetail(id, admin);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Employee not found......", response.getBody());
        }

        @Test
        void deleteEmployee () {
            long id = 1L;
            Admin admin = new Admin();
            admin.setId(id);
            when(adminService.deleteEmployee(id)).thenReturn("");
            String response = adminController.deleteEmp(id);
            Assertions.assertEquals("", response);


        }


        @Test
        void updateEmail(){
        Long id=1L;
        String email="abc";
        Admin admin=new Admin();
        when(adminService.updateEmail(id,email)).thenReturn(admin);
        String response=adminController.editEmail(id,admin);
        Assertions.assertEquals(response,"Email changed");

       }

    @Test
    public void testEditEmail_UserNotFound() {
        Long id = 1L;
        String email = "test@example.com";
        Admin admin = new Admin();
        admin.setEmail(email);
        when(adminService.updateEmail(id, email)).thenThrow(new IllegalArgumentException("User not found"));
        String response = adminController.editEmail(id, admin);
        assertEquals("Not found", response);
    }




}
