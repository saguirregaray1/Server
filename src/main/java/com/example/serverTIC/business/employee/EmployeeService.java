package com.example.serverTIC.business.employee;

import com.example.serverTIC.business.activity.ActivityRepository;
import com.example.serverTIC.business.appuser.AppUserRepository;
import com.example.serverTIC.business.company.CompanyRepository;
import com.example.serverTIC.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService{

    private final EmployeeRepository employeeRepository;

    private final ActivityRepository activityRepository;

    private final AppUserRepository appUserRepository;

    private final CompanyRepository companyRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, AppUserRepository appUserRepository, ActivityRepository activityRepository, CompanyRepository companyRepository){
        this.employeeRepository = employeeRepository;
        this.appUserRepository = appUserRepository;
        this.activityRepository = activityRepository;
        this.companyRepository = companyRepository;
    }

    public void addNewEmployee(Employee employee){
        Optional<Company> temp=companyRepository.findById(employee.getCompany().getId());
        if(temp.isEmpty()){
            throw new IllegalStateException("compañía no existe");
        }
        Company company = temp.get();
        company.addEmployee(employee);
        employeeRepository.save(employee);
        companyRepository.save(company);
        appUserRepository.save(new AppUser(employee.getEmail(),employee.getPassword(), AppUserRole.EMPLOYEE, employee.getId()));

    }

    public List<Employee> getEmployees(){ return employeeRepository.findAll();}

    public void deleteEmployee(Long cedulaEmp) {
        Optional<Employee> temp=employeeRepository.findEmployeeByCedula(cedulaEmp);
        if(temp.isEmpty()){
            throw new IllegalStateException("employee is not registered");
        }
        //borrar de compañia
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
            employee1.setCompany(employee.getCompany());
            employee1.setSaldo(employee.getSaldo());
            employee1.setEmail(employee.getEmail());
            employee1.setPassword(employee.getPassword());
            AppUser appUser=appUserRepository.findById(employee.getId()).get();
            appUser.setEmail(employee.getEmail());
            appUser.setPassword(employee.getPassword());
            employeeRepository.save(employee1); //necesario?
            appUserRepository.save(appUser);
        }
    }

    public void addFavouriteActivity(Long activityId, AppUser appUser){
        Optional<Employee> emp=employeeRepository.findById(appUser.getAssociatedId());
        Optional<Activity> act=activityRepository.findById(activityId);
        if(emp.isEmpty() || act.isEmpty()){
            throw new IllegalStateException("empleado o actividad no existen");
        }
        Employee employee=emp.get();
        Activity activity=act.get();
        if (!employee.getFavs().contains(activity)){
            employee.addFav(activity);
        }
        employeeRepository.save(employee);
    }

    public List<List> getFavsList(Long userId) {
        Optional<Employee> temp=employeeRepository.findById(userId);
        if(temp.isEmpty()){
            throw new IllegalStateException("empleado no existe");
        }
        return employeeRepository.getFavouriteList(temp.get().getFavs());
    }
}
