package com.example.serverTIC.business.company;

import com.example.serverTIC.business.appuser.AppUserService;
import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.CheckIn;
import com.example.serverTIC.persistence.Company;
import com.example.serverTIC.persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path= "/company")
public class CompanyController {

    private final CompanyService companyService;

    private final AppUserService appUserService;
    @Autowired
    public CompanyController(CompanyService companyService, AppUserService appUserService) {
        this.companyService = companyService;
        this.appUserService = appUserService;

    }

    @GetMapping
    public List<Company> getListOfCompanies(){ return companyService.getCompanies(); }

    @PostMapping
    public ResponseEntity<?> registerNewCompany(@RequestBody List<String> inputs){
        return companyService.addNewCompany(inputs);
    }

    @DeleteMapping(path="{companyName}")
    public void deleteCompany(@PathVariable String companyName){
        companyService.deleteCompany(companyName);
    }

    @PutMapping(path="/{companyId}")
    public void updateCompany(@PathVariable Long companyId, @RequestBody Company company) { companyService.updateCompany(company,companyId);}

    @GetMapping(path="/{companyId}")
    public List<Employee> getCompanyEmployees(@PathVariable Long companyId) {return companyService.getCompanyEmployees(companyId);}
    //CompanyUser
    @PostMapping(path = "/user/{companyId}")
    public void registerNewCompanyUser(@PathVariable Long companyId,@RequestBody AppUser appUser) {
        appUserService.addNewCompanyUser(appUser,companyId);
    }

    @DeleteMapping(path="/user/{userId}")
    public void deleteCompanyUser(@PathVariable Long userId){ appUserService.deleteAppUser(userId);}

    @GetMapping(path="/user")
    public List<AppUser> getListOfCompanyUsers(){ return appUserService.getAppUsers();}

    @PutMapping(path="/user")
    public void updateCompanyUser(@RequestBody AppUser appUser) {appUserService.updateAppUser(appUser);}

    @PostMapping(path = "/costs")
    public ResponseEntity getCompanyCosts(@RequestBody List<String> inputs) {
        return companyService.getCostsForTheMonth(Long.parseLong(inputs.get(0)),inputs.get(1));
    }

    @PostMapping(path = "/checkIn")
    public List<CheckIn> getCompanyCheckInList(@RequestBody List<String> inputs) {
        return companyService.getCheckInListForTheMonth(Long.parseLong(inputs.get(0)),inputs.get(1));
    }

    @PutMapping(path = "/checkIn")
    public List<Employee> getCompanyCheckInListUtil(@RequestBody List<String> inputs) {
        return companyService.getCheckInListForTheMonthUtil(Long.parseLong(inputs.get(0)),inputs.get(1));
    }


}