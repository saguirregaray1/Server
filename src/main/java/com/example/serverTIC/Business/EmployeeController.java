package com.example.serverTIC.Business;

import com.example.serverTIC.Persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "/employee")
public class EmployeeController {
    private final Service service;
    @Autowired
    public EmployeeController(Service service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getListOfEmployees(){
        return service.getEmployees();
    }

    @PostMapping
    public void registerNewEmployee(@RequestBody Employee employee){
        service.addNewEmployee(employee);
    }
}
