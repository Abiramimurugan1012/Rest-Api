package com.example.Spring.Controller;


import com.example.Spring.Model.Login;
import com.example.Spring.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class  LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/signup")
    public String signup(@RequestBody Login login){
        return loginService.signup(login);

    }

    @PostMapping("/login")
    public String login(@RequestBody Login login){
        return loginService.login(login);
    }
}
