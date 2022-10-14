package com.example.serverTIC.business.company;
import com.example.serverTIC.persistence.Company;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void addNewCompany(Company company){ companyRepository.save(company);}

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
        if (temp.isEmpty()) {
            addNewCompany(company);
        } else {
            Company company1 = temp.get();
            company1.setNombre(company.getNombre());
            company1.setNroCuenta(company.getNroCuenta());
            company1.setCompanyEmployees(company.getCompanyEmployees());
            companyRepository.save(company1);
        }
    }
 }

