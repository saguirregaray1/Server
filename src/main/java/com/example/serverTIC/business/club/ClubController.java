package com.example.serverTIC.business.club;


import com.example.serverTIC.business.activity.ActivityService;
import com.example.serverTIC.business.appuser.AppUserService;
import com.example.serverTIC.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/club")
public class ClubController {
    private final ClubService clubService;

    private final ActivityService activityService;

    private final AppUserService appUserService;

    @Autowired
    public ClubController(ClubService service, ActivityService activityService, AppUserService appUserService) {
        this.clubService = service;
        this.activityService = activityService;
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<Club> getListOfClubs() {
        return clubService.getClubs();
    }

    @PostMapping
    public ResponseEntity<?> registerNewClub(@RequestBody List<String> inputs) {
        return clubService.addNewClub(inputs);
    }

    @DeleteMapping(path = "{clubName}")
    public void deleteClub(@PathVariable String clubName) {
        clubService.deleteClub(clubName);
    }

    @PutMapping(path = "{clubId}")
    public void updateClub(@PathVariable Long clubId, @RequestBody Club club) {
        clubService.updateClub(club, clubId);
    }

    @GetMapping(path = "/{clubId}")
    public List<List> getListOfClubActivities(@PathVariable Long clubId) {
        return activityService.getActivitiesByClub(clubId);
    }


    //ClubUser
    @PostMapping(path = "/user/{clubId}")
    public ResponseEntity<?> registerNewClubUser(@PathVariable Long clubId, @RequestBody AppUser appUser) {
        return appUserService.addNewClubUser(appUser, clubId);
    }

    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<?> deleteClubUser(@PathVariable Long userId) {
        return appUserService.deleteAppUser(userId);
    }

    @GetMapping(path = "/user")
    public List<AppUser> getListOfClubUsers() {
        return appUserService.getAppUsers();
    }

    @PutMapping(path = "/user")
    public void updateClubUser(@RequestBody AppUser appUser) {
        appUserService.updateAppUser(appUser);
    }


    // Activity
    @GetMapping(path = "/activity")
    public List<List> getListOfActivities() {
        return activityService.getActivities();
    }

    @PostMapping(path = "/activity")
    public ResponseEntity<?> registerNewActivity(@RequestBody Activity activity) {
        return activityService.addNewActivity(activity);
    }

    @DeleteMapping(path = "/activity/{activityName}")
    public void deleteActivity(@PathVariable String activityName, @RequestBody Club club) {
        activityService.deleteActivity(activityName, club);
    }

    @PutMapping(path = "/activity")
    public void updateActivity(@RequestBody Activity activity) {
        activityService.updateActivity(activity);
    }

    //registerToActivity
    @PostMapping(path = "/activity/register")
    public ResponseEntity<?> makeAReservation(@RequestBody List<String> inputs) {
        return activityService.makeReservation(Long.parseLong(inputs.get(0)), Long.parseLong(inputs.get(1)), inputs.get(2));
    }

    //cancelRegistration

    @PostMapping(path = "/activity/checkIn")
    public ResponseEntity<?> cameToActivityWithReservation(@RequestBody List<String> inputs) {
        return activityService.cameToActivity(Long.parseLong(inputs.get(0)), Long.parseLong(inputs.get(1)), inputs.get(2));
    }
    @PutMapping(path= "/activity/checkIn")
    public ResponseEntity cameToActivityWithoutReservation(@RequestBody List<String> inputs) {
        return activityService.cameToActivityWithNoReservation(Long.parseLong(inputs.get(0)), Long.parseLong(inputs.get(1)), inputs.get(2));
    }


    //filter
    @GetMapping(path = "/activity/{category}")
    public List<List> getListOfActivitiesByCategory(@PathVariable String category) {
        return activityService.getActivitiesByCategory(category);
    }

    @GetMapping(path = "/activity/quota/{activityId}")
    public List<Quota> getQuotaOfActivity(@PathVariable Long activityId) {
        return activityService.getActivityQuota(activityId);
    }

    @PostMapping(path = "/earnings")
    public ResponseEntity getClubEarnings(@RequestBody List<String> inputs) {
        return clubService.getClubEarningsForTheMonth(Long.parseLong(inputs.get(0)),inputs.get(1));
    }

    @PostMapping(path = "/checkIn")
    public List<CheckIn> getClubCheckInList(@RequestBody List<String> inputs) {
        return clubService.getCheckInListForTheMonth(Long.parseLong(inputs.get(0)),inputs.get(1));
    }

    @PutMapping(path = "/checkIn")
    public List<Employee> getClubCheckInListUtil(@RequestBody List<String> inputs) {
        return clubService.getCheckInListForTheMonthUtil(Long.parseLong(inputs.get(0)),inputs.get(1));
    }

    @PostMapping(path= "/activity/byName")
    public ResponseEntity getActivityByNombre(@RequestBody List<String> inputs){
        return activityService.getActivityByNombre(inputs.get(0),inputs.get(1));
    }
}

