package com.example.serverTIC.business.company;

import com.example.serverTIC.business.employee.EmployeeService;
import com.example.serverTIC.persistence.Company;
import com.example.serverTIC.persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path= "/company/")
public class CompanyController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;
    @Autowired
    public CompanyController(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService= employeeService;
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

    @GetMapping(path="/employee/{cedula}")
    public Optional<Employee> getEmployee(@PathVariable Long cedula){ return employeeService.getEmployee(cedula); }

    @PostMapping(path="/employee")   //agregar companyId
    public void registerNewEmployee(@RequestBody Employee employee){
        employeeService.addNewEmployee(employee);
    }

   /* @PutMapping(path="/employee/{employeeId}")
    public void updateEmployee(@PathVariable Long employeeId, @RequestBody Employee employee){
        employeeService.updateEmployee(employee,employeeId);
    }  */

}