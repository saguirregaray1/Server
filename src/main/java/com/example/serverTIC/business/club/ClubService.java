package com.example.serverTIC.business.club;

import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.AppUserRole;
import com.example.serverTIC.persistence.Club;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public ResponseEntity<?> addNewClub(List<String> inputs) {
        Club club=new Club(inputs.get(0),inputs.get(1));
        club.addClubUser(new AppUser(inputs.get(2),inputs.get(3), AppUserRole.CLUB_USER,club));
        clubRepository.save(club);
        return new ResponseEntity<>(club,HttpStatus.OK);
    }

    public List<Club> getClubs(){
        return clubRepository.findAll();
    }

    public void deleteClub(String clubName) {
        Optional<Club> temp=clubRepository.findClubByNombre(clubName);
        if(temp.isEmpty()){
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

}
