package com.example.serverTIC.business.club;


import com.example.serverTIC.business.activity.ActivityService;
import com.example.serverTIC.business.employee.EmployeeService;
import com.example.serverTIC.persistence.Activity;
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

     private final ActivityService activityService;
     @Autowired
     public ClubController(ClubService service, ActivityService activityService) {
         this.clubService = service;
         this.activityService = activityService;
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

        //updateClub


        // Activity
        @GetMapping(path="/activity")
        public List<Activity> getListOfActivities(){
         return activityService.getActivities();
        }

        @PostMapping(path="/activity")
        public void registerNewActivity(@RequestBody Activity activity) {activityService.addNewActivity(activity);}

        @DeleteMapping(path="/activity/{activityName}")
        public void deleteActivity(@PathVariable String activityName)   {activityService.deleteActivity(activityName); }

        //registerToActivity
        @PostMapping(path="/activity/{activityId}{empId}")
        public boolean registerToActivity(@PathVariable Long activityId, Long employeeId){
            return activityService.registerToActivity(activityId,employeeId);
        }

        //filter
        @GetMapping(path="/activity/{category}")
        public Optional<Activity> getListOfActivitiesByCategory(@PathVariable int category) {
            return activityService.getActivitiesByCategory(category);
        }

        //cancelActivity


        //updateActivity


    }

