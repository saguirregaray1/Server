package com.example.serverTIC.business.employee;

import com.example.serverTIC.business.appuser.AppUserRepository;
import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.AppUserRole;
import com.example.serverTIC.persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService{

    private final EmployeeRepository employeeRepository;

    private final AppUserRepository appUserRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, AppUserRepository appUserRepository){
        this.employeeRepository = employeeRepository;
        this.appUserRepository = appUserRepository;
    }

    public void addNewEmployee(Employee employee){
        employeeRepository.save(employee);
        appUserRepository.save(new AppUser(employee.getEmail(),employee.getPassword(), AppUserRole.EMPLOYEE));
    }

    public List<Employee> getEmployees(){ return employeeRepository.findAll();}

    public void deleteEmployee(Long cedulaEmp) {
        Optional<Employee> temp=employeeRepository.findEmployeeByCedula(cedulaEmp);
        if(temp.isEmpty()){
            throw new IllegalStateException("employee is not registered");
        }
        employeeRepository.deleteById(temp.get().getId());
    }

    public Optional<Employee> getEmployee(Long cedula) {
        return employeeRepository.findEmployeeByCedula(cedula);
    }

    public boolean isAnEmployee(Long cedula) {
        return getEmployee(cedula).isPresent();
    }


    public void updateEmployee(Employee employee, Long cedula) {
        Optional<Employee> temp=employeeRepository.findEmployeeByCedula(cedula);
        if(temp.isEmpty()){
            addNewEmployee(employee);
        }
        else {
            Employee employee1 = temp.get();
            employee1.setCompanyId(employee.getCompanyId());
            employee1.setSaldo(employee.getSaldo());
            employee1.setCedula(employee.getCedula());
            employee1.setEmail(employee.getEmail());
            employee1.setId(employee.getId());
        }
    }
}
