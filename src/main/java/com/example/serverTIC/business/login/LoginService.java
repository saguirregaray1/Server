package com.example.serverTIC.business.login;

import com.example.serverTIC.business.appuser.AppUserRepository;
import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.AppUserRole;
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
        ResponseEntity<?> response=new ResponseEntity<>(appUser,HttpStatus.OK);
        return response;
    }

    public ResponseEntity getDependenceEntity(Long id){
        Optional<AppUser> user=appUserRepository.findById(id);
        if (user.isPresent()){
            AppUser appUser = user.get();
            if (appUser.getAppUserRole().equals(AppUserRole.COMPANY_USER)){
                return new ResponseEntity(appUser.getCompany(),HttpStatus.OK);
            } else if (appUser.getAppUserRole().equals(AppUserRole.EMPLOYEE)) {
                return new ResponseEntity(appUser.getEmployee(),HttpStatus.OK);
            } else if (appUser.getAppUserRole().equals(AppUserRole.CLUB_USER)) {
                return new ResponseEntity(appUser.getClub(),HttpStatus.OK);
            }else{
                return new ResponseEntity(appUser.getAdmin(),HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
