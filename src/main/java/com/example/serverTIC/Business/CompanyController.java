package com.example.serverTIC.Business;

import com.example.serverTIC.Persistence.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path= "/company")
public class CompanyController {
    private final Service service;
    @Autowired
    public CompanyController(Service service) {
        this.service = service;
    }

    @GetMapping
    public List<Company> getListOfCompanies(){ return service.getCompanies(); }

    @PostMapping
    public void registerNewCompany(@RequestBody Company company){
        service.addNewCompany(company);
    }

    @DeleteMapping(path="{companyName}")
    public void deleteCompany(@PathVariable String companyName){
        service.deleteCompany(companyName);
    }
}