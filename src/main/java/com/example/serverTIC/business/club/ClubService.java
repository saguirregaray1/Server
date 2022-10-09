package com.example.serverTIC.business.club;

import com.example.serverTIC.persistence.Club;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void addNewClub(Club club) {
        clubRepository.save(club);
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
        if (temp.isEmpty()) {
            addNewClub(club);
        } else {
            Club club1 = temp.get();
            club1.setEmail(club.getEmail());
            club1.setId(club.getId());
            club1.setNombre(club.getNombre());
            club1.setPassword(club.getPassword());
            club1.setDir(club.getDir());
        }
    }

}
