package com.example.serverTIC.business.company;
import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.AppUserRole;
import com.example.serverTIC.persistence.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public ResponseEntity<?> addNewCompany(List<String> inputs){
        Company company = new Company(inputs.get(0),Long.parseLong(inputs.get(1)));
        company.addCompanyUser(new AppUser(inputs.get(2),inputs.get(3), AppUserRole.COMPANY_USER,company));
        companyRepository.save(company);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    public List<Company> getCompanies(){ return companyRepository.findAll();}

    public void deleteCompany(String companyName) {
        Optional<Company> temp=companyRepository.findCompanyByNombre(companyName);
        if(temp.isEmpty()){
            throw new IllegalStateException("company is not registered");
        }
        companyRepository.deleteById(temp.get().getId());
    }


    public void updateCompany(Company company, Long companyId) {
        Optional<Company> temp = companyRepository.findById(companyId);
        if (temp.isPresent()) {
            Company company1 = temp.get();
            company1.setNombre(company.getNombre());
            company1.setNroCuenta(company.getNroCuenta());
            company1.setCompanyEmployees(company.getCompanyEmployees());
            company1.setCompanyUsers(company.getCompanyUsers());
            companyRepository.save(company1);
        }
    }
 }

