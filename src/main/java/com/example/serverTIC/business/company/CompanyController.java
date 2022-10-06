package com.example.serverTIC.business.company;

import com.example.serverTIC.persistence.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path= "/company")
public class CompanyController {

    private final CompanyService companyService;
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getListOfCompanies(){ return companyService.getCompanies(); }

    @PostMapping
    public void registerNewCompany(@RequestBody Company company){
        companyService.addNewCompany(company);
    }

    @DeleteMapping(path="{companyName}")
    public void deleteCompany(@PathVariable String companyName){
        companyService.deleteCompany(companyName);
    }

    //updateCompany

}