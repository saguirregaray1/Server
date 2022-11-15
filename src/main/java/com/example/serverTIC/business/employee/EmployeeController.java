package com.example.serverTIC.business.employee;

import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.Employee;
import com.example.serverTIC.persistence.Reservation;
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
    public ResponseEntity<?> registerNewEmployee(@RequestBody Employee employee){
        return this.employeeService.addNewEmployee(employee);
    }

    @DeleteMapping(path="{cedula}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long cedula){ return employeeService.deleteEmployee(cedula); }

    @PutMapping
    public void updateEmployee(@RequestBody Employee employee){
        employeeService.updateEmployee(employee);
    }

    @GetMapping(path="{cedula}")
    public ResponseEntity<?> isAnEmployee(@PathVariable Long cedula){ return employeeService.isAnEmployee(cedula); }

    @PostMapping(path="{activityId}")
    public ResponseEntity<?> addToFavouriteList(@PathVariable Long activityId, @RequestBody AppUser appUser) {
        return employeeService.addFavouriteActivity(activityId,appUser);}

    @GetMapping(path="/favourite/{userId}")
    public List<List> getFavouriteList(@PathVariable Long userId) {
        return employeeService.getFavsList(userId);}

    @GetMapping(path= "/reservations/{userId}")
    public List<Reservation> getPendingReservations(@PathVariable Long userId){
        return employeeService.getPendingReservations(userId);
    }

}
