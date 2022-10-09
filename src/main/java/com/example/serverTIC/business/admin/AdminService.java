package com.example.serverTIC.business.admin;

import com.example.serverTIC.business.appuser.AppUserRepository;
import com.example.serverTIC.persistence.Admin;
import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    private final AppUserRepository appUserRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, AppUserRepository appUserRepository) {
        this.adminRepository = adminRepository;
        this.appUserRepository = appUserRepository;
    }

    public void addNewAdmin(Admin admin) {

        adminRepository.save(admin);
        appUserRepository.save(new AppUser(admin.getEmail(), admin.getPassword(), AppUserRole.ADMIN));
    }

    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public void deleteAdmin(Long id) {
        Optional<Admin> temp= adminRepository.findById(id);
        if(temp.isEmpty()){
            throw new IllegalStateException("club is not registered");
        }
        adminRepository.deleteById(id);
    }

    public void updateAdmin(Admin admin, Long adminId) {
        Optional<Admin> temp =adminRepository.findById(adminId);
        if(temp.isEmpty()){
            addNewAdmin(admin);
        }
        else{
            Admin admin1=temp.get();
            admin1.setId(admin.getId());
            admin1.setEmail(admin.getEmail());
            admin1.setPassword(admin.getPassword());
        }
    }
}
