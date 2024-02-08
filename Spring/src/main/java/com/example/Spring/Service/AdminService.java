package com.example.Spring.Service;


import com.example.Spring.Model.Admin;
import com.example.Spring.Repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    public List<Admin> getAllEmployee() {
        return adminRepo.findAll();
    }

    public Admin addEmployee(@RequestBody Admin admin) {
        return adminRepo.save(admin);
    }

    public Optional<Admin> getEmployeeById(Long id) {
        return Optional.ofNullable(adminRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found")));
    }

    public Admin updateEmployee(Long id, Admin admin) {
        Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found")));
        Admin admin1 = optionalAdmin.get();
        admin1.setFname(admin.getFname());
        admin1.setLname(admin.getLname());
        admin1.setEmail(admin.getEmail());
        return adminRepo.save(admin1);

    }

    public String deleteEmployee(Long id){
        if(adminRepo.findById(id).isPresent()){
            adminRepo.deleteById(id);
            return "Deleted Successfully!!!!!!!";
        }
        else {
            return "Not found.....";
        }

    }

}
