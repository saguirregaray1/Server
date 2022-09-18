package com.example.serverTIC.Business;


import com.example.serverTIC.Persistence.Club;
import com.example.serverTIC.Persistence.Company;
import com.example.serverTIC.Persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

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

    public void deleteClub(String clubName) {
        Optional<Club> temp=clubRepository.findClubByNombre(clubName);
        if(temp.isEmpty()){
            throw new IllegalStateException("club is not registered");
        }
        clubRepository.deleteById(temp.get().getId());
    }

    public void deleteCompany(String companyName) {
        Optional<Company> temp=companyRepository.findCompanyByNombre(companyName);
        if(temp.isEmpty()){
            throw new IllegalStateException("company is not registered");
        }
        companyRepository.deleteById(temp.get().getId());
    }

    public void deleteEmployee(Long cedulaEmp) {
        Optional<Employee> temp=employeeRepository.findEmployeeByCedula(cedulaEmp);
        if(temp.isEmpty()){
            throw new IllegalStateException("employee is not registered");
        }
        employeeRepository.deleteById(temp.get().getId());
    }
}