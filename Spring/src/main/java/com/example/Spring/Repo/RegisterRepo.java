package com.example.Spring.Repo;

import com.example.Spring.Model.Login;
import com.example.Spring.Model.Register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepo extends JpaRepository<Register,Long> {
    Register findByUsername(String username);

}
