package com.example.Spring.Repo;

import com.example.Spring.Model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepo extends JpaRepository<Login,Long> {
       Login findByUsername(String username);
}
