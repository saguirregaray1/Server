package com.example.serverTIC.business.employee;

import com.example.serverTIC.business.activity.ActivityService;
import com.example.serverTIC.persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    private final ActivityService activityService;
    @Autowired
    public EmployeeController(EmployeeService employeeService, ActivityService activityService) {
        this.employeeService = employeeService;
        this.activityService = activityService;
    }

    @GetMapping
    public List<Employee> getListOfEmployees(){
        return employeeService.getEmployees();
    }

    @PostMapping
    public void registerNewEmployee(@RequestBody Employee employee){
        employeeService.addNewEmployee(employee);
    }

    @DeleteMapping(path="{cedula}")
    public void deleteEmployee(@PathVariable Long cedula){ employeeService.deleteEmployee(cedula); }

     /* @PutMapping(path="/employee/{cedula}")
    public void updateEmployee(@PathVariable Long employeeId, @RequestBody Employee employee){
        employeeService.updateEmployee(employee,employeeId);
    }  */

    @GetMapping(path="{cedula}")
    public boolean isAnEmployee(@PathVariable Long cedula){ return employeeService.isAnEmployee(cedula); }

    //addFavourite



}
