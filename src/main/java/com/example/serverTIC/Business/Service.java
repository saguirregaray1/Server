package com.example.serverTIC.Business;


import com.example.serverTIC.Persistence.Club;
import com.example.serverTIC.Persistence.Company;
import com.example.serverTIC.Persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private final ClubRepository clubRepository;
    private final CompanyRepository companyRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public Service(ClubRepository clubRepository, CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.clubRepository = clubRepository;
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Club> getClubs(){
        return clubRepository.findAll();
    }

    public void addNewClub(Club club) {
        clubRepository.save(club);
    }

    public List<Company> getCompanies(){ return companyRepository.findAll();}

    public void addNewCompany(Company company){ companyRepository.save(company);}

    public List<Employee> getEmployees(){ return employeeRepository.findAll();}

    public void addNewEmployee(Employee employee){ employeeRepository.save(employee);}

}