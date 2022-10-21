package com.example.serverTIC.business.appuser;

import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.AppUserRole;
import net.bytebuddy.pool.TypePool;
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
        Optional<AppUser> temp=appUserRepository.findById(appUser.getId());
        if (temp.isPresent()){
            throw new IllegalStateException("usuario ya existe");
        }
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

    public void updateAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }
}


