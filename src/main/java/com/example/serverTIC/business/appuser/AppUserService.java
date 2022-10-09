package com.example.serverTIC.business.appuser;

import com.example.serverTIC.persistence.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public void addNewAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    public List<AppUser> getAppUsers(){
        return appUserRepository.findAll();
    }

    public void deleteAppUser(Long id) {
        Optional<AppUser> temp= appUserRepository.findById(id);
        if(temp.isEmpty()){
            throw new IllegalStateException("club is not registered");
        }
        appUserRepository.deleteById(id);
    }

    public void updateAppUser(AppUser appUser, Long userId) {
        Optional<AppUser> temp = appUserRepository.findById(userId);
        if (temp.isEmpty()) {
            addNewAppUser(appUser);
        } else {
            AppUser appUser1 = temp.get();
            appUser1.setEmail(appUser.getEmail());
            appUser1.setId(appUser.getId());
            appUser1.setPassword(appUser.getPassword());
            appUser1.setAppUserRole(appUser.getAppUserRole());
        }
    }
}


