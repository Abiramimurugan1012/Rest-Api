package com.example.Spring.Controller;

import com.example.Spring.Model.Admin;
import com.example.Spring.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emp/api")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public List<Admin> getAll() {
        return adminService.getAllEmployee();
    }

    @PostMapping("/")
    public Admin addDetail(@RequestBody Admin admin) {
        return adminService.addEmployee(admin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getId(@PathVariable Long id) {
        Object admin = adminService.getEmployeeById(id);
            if(admin!=null) {
                return new ResponseEntity<>(admin, HttpStatus.OK);
            }
        else {
            return new ResponseEntity<>("Employee not found.....", HttpStatus.NOT_FOUND);
             }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateDetail(@PathVariable Long id, @RequestBody Admin admindetails) {
        try {
             adminService.updateEmployee(id, admindetails);
            return new ResponseEntity<>("Successfully updated!!!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Employee not found......", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/del/{id}")
    public String deleteEmp(@PathVariable Long id){
        return adminService.deleteEmployee(id);
    }

    @PatchMapping("/patch/{id}")
    public String editEmail(@PathVariable Long id,@RequestBody Admin admin){
        try {
            String email=admin.getEmail();
            adminService.updateemail(id,email);
            return "Email changed";
        }
        catch (IllegalArgumentException e){
            return "Not found";
        }
    }


}
