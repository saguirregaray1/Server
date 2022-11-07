package com.example.serverTIC.business.employee;

import com.example.serverTIC.business.activity.ActivityRepository;
import com.example.serverTIC.business.appuser.AppUserRepository;
import com.example.serverTIC.business.company.CompanyRepository;
import com.example.serverTIC.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        companyRepository.save(company);
    }


    @Transactional
    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
        }

    public void deleteEmployee(Long cedulaEmp) {
        Optional<Employee> temp=employeeRepository.findEmployeeByCedula(cedulaEmp);
        if(temp.isEmpty()){
            throw new IllegalStateException("employee is not registered");
        }
        //borrar de compañia
        employeeRepository.deleteById(temp.get().getId());
    }


    public ResponseEntity<Employee> isAnEmployee(Long cedula) {
        Optional<Employee> emp = employeeRepository.findEmployeeByCedula(cedula);
        if (emp.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(emp.get(),HttpStatus.OK);
    }


    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void addFavouriteActivity(Long activityId, AppUser appUser){
        AppUser user=appUserRepository.findById(appUser.getId()).get();
        Optional<Employee> emp=employeeRepository.findById(user.getEmployee().getId());
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
        AppUser appUser=appUserRepository.findById(userId).get();
        Optional<Employee> temp=employeeRepository.findById(appUser.getEmployee().getId());
        if(temp.isEmpty()){
            throw new IllegalStateException("empleado no existe");
        }
        return employeeRepository.getFavouriteList(temp.get().getFavs());
    }
}
