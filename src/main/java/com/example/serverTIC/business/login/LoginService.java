package com.example.serverTIC.business.login;

import com.example.serverTIC.business.appuser.AppUserRepository;
import com.example.serverTIC.persistence.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public LoginService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }


    public ResponseEntity isLoginCorrect(LoginRequest loginRequest) {
        Optional<AppUser> temp=appUserRepository.findAppUserByEmail(loginRequest.getEmail());

        if (temp.isEmpty() || !temp.get().getPassword().equals(loginRequest.getPassword())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AppUser appUser=temp.get();

        return new ResponseEntity<>(appUser,HttpStatus.OK);

    }
}
