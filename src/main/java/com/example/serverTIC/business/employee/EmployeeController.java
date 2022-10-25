package com.example.serverTIC.business.employee;

import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.Employee;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        this.employeeService.addNewEmployee(employee);
    }

    @DeleteMapping(path="{cedula}")
    public void deleteEmployee(@PathVariable Long cedula){ employeeService.deleteEmployee(cedula); }

    @PutMapping
    public void updateEmployee(@RequestBody Employee employee){
        employeeService.updateEmployee(employee);
    }

    @GetMapping(path="{cedula}")
    public ResponseEntity isAnEmployee(@PathVariable Long cedula){ return employeeService.isAnEmployee(cedula); }

    @PostMapping(path="{activityId}")
    public void addToFavouriteList(@PathVariable Long activityId, @RequestBody AppUser appUser) {
        employeeService.addFavouriteActivity(activityId,appUser);}

    @GetMapping(path="/favourite/{userId}")
    public List<List> getFavouriteList(@PathVariable Long userId) {
        return employeeService.getFavsList(userId);}

}
