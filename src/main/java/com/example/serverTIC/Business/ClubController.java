package com.example.serverTIC.Business;


import com.example.serverTIC.Persistence.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 @RestController
 @RequestMapping(path= "/club")
 public class ClubController {
     private final Service service;
     @Autowired
     public ClubController(Service service) {
         this.service = service;
        }

        @GetMapping
        public List<Club> getListOfClubs(){
            return service.getClubs();
        }

        @PostMapping
        public void registerNewClub(@RequestBody Club club){
            service.addNewClub(club);
        }
        @DeleteMapping(path="{clubName}")
        public void deleteClub(@PathVariable String clubName){
         service.deleteClub(clubName);
        }
    }

