package com.example.serverTIC.business.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){return loginService.isLoginCorrect(loginRequest);}

    @GetMapping(path="{id}")
    public ResponseEntity getDependenceEntity(@PathVariable Long id){
        return loginService.getDependenceEntity(id);
    }
}
