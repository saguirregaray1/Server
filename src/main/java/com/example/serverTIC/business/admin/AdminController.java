package com.example.serverTIC.business.admin;


import com.example.serverTIC.persistence.Admin;
import com.example.serverTIC.persistence.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/admin")
public class AdminController {

    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<Admin> getListOfAdmins()  {return adminService.getAdmins(); }

    @PostMapping
    public void registerNewAdmin(@RequestBody Admin admin) {adminService.addNewAdmin(admin);}

    @DeleteMapping(path="{adminId}")
    public void deleteAdmin(@PathVariable Long adminId) {adminService.deleteAdmin(adminId);}

    @PutMapping(path="{adminId}")
    public void updateAdmin(@PathVariable Long adminId, @RequestBody Admin admin) {adminService.updateAdmin(admin,adminId);}



}
