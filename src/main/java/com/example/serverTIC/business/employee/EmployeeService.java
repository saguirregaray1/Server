package com.example.serverTIC.business.employee;

import com.example.serverTIC.persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }

    public void addNewEmployee(Employee employee){ employeeRepository.save(employee);}

    public List<Employee> getEmployees(){ return employeeRepository.findAll();}

    public void deleteEmployee(Long cedulaEmp) {
        Optional<Employee> temp=employeeRepository.findEmployeeByCedula(cedulaEmp);
        if(temp.isEmpty()){
            throw new IllegalStateException("employee is not registered");
        }
        employeeRepository.deleteById(temp.get().getId());
    }

    public Optional<Employee> getEmployee(Long cedula) {
        Optional<Employee> temp=employeeRepository.findEmployeeByCedula(cedula);
        return temp;
    }

    public boolean isAnEmployee(Long cedula) {
        return getEmployee(cedula).isPresent();
    }


}
