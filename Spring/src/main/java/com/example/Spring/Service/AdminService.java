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

    public Object getEmployeeById(Long id) {
        Optional<Admin> demo=adminRepo.findById(id);
        if (demo.isPresent()){
            return demo;
        }else {
            return "Employee not found......";
        }
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

    public Admin updateEmail(Long id,String email){
        Optional<Admin> admin=adminRepo.findById(id);
        if (admin.isPresent()){
            Admin user=admin.get();
            user.setEmail(email);
            return adminRepo.save(user);
        }
        else {
            throw new IllegalArgumentException("User not found");
        }
    }

}
