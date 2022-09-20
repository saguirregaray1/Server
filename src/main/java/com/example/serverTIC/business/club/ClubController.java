package com.example.serverTIC.business.club;


import com.example.serverTIC.business.employee.EmployeeService;
import com.example.serverTIC.persistence.Club;
import com.example.serverTIC.persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
 @RequestMapping(path= "/club")
 public class ClubController {
     private final ClubService clubService;
     private final EmployeeService employeeService;
     @Autowired
     public ClubController(ClubService service, EmployeeService employeeService) {
         this.clubService = service;
         this.employeeService = employeeService;
        }

        @GetMapping
        public List<Club> getListOfClubs(){
            return clubService.getClubs();
        }

        @PostMapping
        public void registerNewClub(@RequestBody Club club){
            clubService.addNewClub(club);
        }

        @DeleteMapping(path="{clubName}")
        public void deleteClub(@PathVariable String clubName){
         clubService.deleteClub(clubName);
        }

        @GetMapping(path="/employee/{cedula}")
        public boolean isAnEmployee(@PathVariable Long cedula){ return employeeService.isAnEmployee(cedula); }

    }

