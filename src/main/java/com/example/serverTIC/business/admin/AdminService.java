package com.example.serverTIC.business.admin;

import com.example.serverTIC.business.club.ClubRepository;
import com.example.serverTIC.persistence.Admin;
import com.example.serverTIC.persistence.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void addNewAdmin(Admin admin) {
        adminRepository.save(admin);
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

}
