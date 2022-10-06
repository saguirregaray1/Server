package com.example.serverTIC.business.login;

import com.example.serverTIC.business.admin.AdminRepository;
import com.example.serverTIC.persistence.Admin;
import org.apache.coyote.Response;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final AdminRepository adminRepository;

    @Autowired
    public LoginService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    public ResponseEntity isLoginCorrect(LoginRequest loginRequest) {
        Optional<Admin> admin = adminRepository.findAdminByMail(loginRequest.getMail());

        if (admin.isEmpty() ||!admin.get().getPassword().equals(loginRequest.getPassword())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Admin administrador=admin.get();

        return new ResponseEntity<>(administrador,HttpStatus.OK);

    }
}
