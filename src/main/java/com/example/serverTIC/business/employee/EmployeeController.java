package com.example.serverTIC.business.employee;

import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
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

    @PutMapping(path="{cedula}")
    public void updateEmployee(@PathVariable Long cedula, @RequestBody Employee employee){
        employeeService.updateEmployee(employee,cedula);
    }

    @GetMapping(path="{cedula}")
    public boolean isAnEmployee(@PathVariable Long cedula){ return employeeService.isAnEmployee(cedula); }

    @PostMapping(path="{activityId}")
    public void addToFavouriteList(@PathVariable Long activityId, @RequestBody Long employeeId) { employeeService.addFavouriteActivity(activityId,employeeId);}

    @GetMapping(path="{employeeId}")
    public List<Activity> getFavouriteList(@PathVariable Long employeeId) { return employeeService.getFavsList(employeeId);}

}
