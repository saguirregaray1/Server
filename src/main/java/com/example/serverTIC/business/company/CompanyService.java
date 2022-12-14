package com.example.serverTIC.business.company;
import com.example.serverTIC.business.employee.EmployeeRepository;
import com.example.serverTIC.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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

    public List<Employee> getCompanyEmployees(Long companyId) {
        Optional<Company> comp=companyRepository.findById(companyId);
        if (comp.isEmpty()){
            return new ArrayList<>();
        }
       return employeeRepository.findEmployeeByCompanyId(comp.get());
    }

    public ResponseEntity getCostsForTheMonth(Long companyId, String fechaMonthYear) {
        Optional<Company> comp=companyRepository.findById(companyId);
        if (comp.isEmpty()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Company company=comp.get();
        long totalCost=0L;
        for (Employee employee:company.getCompanyEmployees()) {
            Optional<Long> tempCost = employeeRepository.findCheckInsCost(employee, fechaMonthYear);
            if (tempCost.isPresent()) {
                totalCost += tempCost.get();
            }
        }
        return new ResponseEntity<>(new Costs(totalCost), HttpStatus.OK);
    }

    public List<CheckIn> getCheckInListForTheMonth(Long companyId, String fechaMonthYear) {
        Optional<Company> comp=companyRepository.findById(companyId);
        if (comp.isEmpty()){
            return new ArrayList<>();
        }
        Company company=comp.get();
        List<CheckIn> totalCheckIns= new ArrayList<>();
        for (Employee employee:company.getCompanyEmployees()){
            totalCheckIns.addAll(employeeRepository.findCheckInList(employee, fechaMonthYear));
        }
        return totalCheckIns;
    }

    public List<Employee> getCheckInListForTheMonthUtil(Long companyId, String fechaMonthYear) {
        Optional<Company> comp=companyRepository.findById(companyId);
        if (comp.isEmpty()){
            return new ArrayList<>();
        }
        Company company=comp.get();
        List<Employee> totalEmployees= new ArrayList<>();
        for (Employee employee:company.getCompanyEmployees()){
            List<CheckIn> temp= employeeRepository.findCheckInList(employee, fechaMonthYear);
            if (!temp.isEmpty()){
                totalEmployees.add(employee);
            }
        }
        return totalEmployees;
    }

}

