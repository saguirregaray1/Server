package com.example.serverTIC.business.club;

import com.example.serverTIC.business.activity.ActivityRepository;
import com.example.serverTIC.persistence.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    private final ActivityRepository activityRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository, ActivityRepository activityRepository) {
        this.clubRepository = clubRepository;
        this.activityRepository = activityRepository;
    }

    public ResponseEntity<?> addNewClub(List<String> inputs) {
        Club club = new Club(inputs.get(0), inputs.get(1));
        club.addClubUser(new AppUser(inputs.get(2), inputs.get(3), AppUserRole.CLUB_USER, club));
        clubRepository.save(club);
        return new ResponseEntity<>(club, HttpStatus.OK);
    }

    public List<Club> getClubs() {
        return clubRepository.findAll();
    }

    public ResponseEntity<?> getClubByNombre(String clubName) {
        Optional<Club> club=clubRepository.findClubByNombre(clubName);
        if (club.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(club.get(),HttpStatus.OK);
    }


    public void deleteClub(String clubName) {
        Optional<Club> temp = clubRepository.findClubByNombre(clubName);
        if (temp.isEmpty()) {
            throw new IllegalStateException("club is not registered");
        }
        clubRepository.deleteById(temp.get().getId());
    }

    public void updateClub(Club club, Long clubId) {
        Optional<Club> temp = clubRepository.findById(clubId);
        if (temp.isPresent()) {
            Club club1 = temp.get();
            club1.setClubActivities(club.getClubActivities());
            club1.setNombre(club.getNombre());
            club1.setDir(club.getDir());
            club1.setClubUsers(club.getClubUsers());
            clubRepository.save(club1);
        }
    }

    public ResponseEntity getClubEarningsForTheMonth(Long clubId, String fechaMesAño) {
        Optional<Club> comp=clubRepository.findById(clubId);
        if (comp.isEmpty()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Club club=comp.get();
        long totalCost=0L;
        for (Activity activity:club.getClubActivities()){
            List<CheckIn> temp=activityRepository.findActivityEarning(activity.getId(),fechaMesAño);
            totalCost+= activity.getPrecio()* temp.size();
        }
        return new ResponseEntity<>(new Costs(totalCost), HttpStatus.OK);
    }

    public List<List> getCheckInListForTheMonth(Long clubId, String fechaMesAño) {
        Optional<Club> temp = clubRepository.findById(clubId);
        if (temp.isEmpty()) {
            throw new IllegalStateException("company not found");
        }
        Club club = temp.get();
        List<List> totalCheckIns = new ArrayList<>();
        for (Activity activity : club.getClubActivities()) {
            totalCheckIns.addAll(activityRepository.findCheckInList(activity, fechaMesAño));
        }
        return totalCheckIns;
    }

    public List<Employee> getCheckInListForTheMonthUtil(Long clubId, String fechaMesAño) {
        Optional<Club> temp = clubRepository.findById(clubId);
        if (temp.isEmpty()) {
            return new ArrayList<>();
        }
        Club club = temp.get();
        List<Employee> totalUsers = new ArrayList<>();
        for (Activity activity : club.getClubActivities()) {
            List<Employee> userCheckIn = activityRepository.findCheckInEmployeeList(activity, fechaMesAño);
            for (Employee user: userCheckIn){
                if (!totalUsers.contains(user)){
                    totalUsers.add(user);
                }
            }
        }
        return totalUsers;
    }
}
