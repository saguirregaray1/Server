package com.example.serverTIC.business.club;


import com.example.serverTIC.business.activity.ActivityService;
import com.example.serverTIC.business.appuser.AppUserService;
import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.ActivityCategories;
import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public void registerNewClub(@RequestBody Club club) {
        clubService.addNewClub(club);
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
    public void registerNewClubUser(@PathVariable Long clubId,@RequestBody AppUser appUser) {
        appUserService.addNewClubUser(appUser,clubId);
    }

    @DeleteMapping(path = "/user/{userId}")
    public void deleteClubUser(@PathVariable Long userId) {
        appUserService.deleteAppUser(userId);
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
    public void registerNewActivity(@RequestBody Activity activity) {
        activityService.addNewActivity(activity);
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
    @PostMapping(path = "/activity/{activityId}")
    public ResponseEntity registerToActivity(@PathVariable Long activityId, @RequestBody AppUser appUser) {
        return activityService.registerToActivity(activityId, appUser);
    }

    //cancelRegistration

    @PutMapping(path = "/activity/{activityId}")
    public ResponseEntity cameToActivity(@PathVariable Long activityId, @RequestBody Long cedula) {
        return activityService.cameToActivity(activityId, cedula);
    }


    //filter
    @GetMapping(path = "/activity/{category}")
    public List<List> getListOfActivitiesByCategory(@PathVariable String category) {
        return activityService.getActivitiesByCategory(category);
    }

}

