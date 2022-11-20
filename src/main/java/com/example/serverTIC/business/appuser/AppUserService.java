package com.example.serverTIC.business.appuser;

import com.example.serverTIC.business.club.ClubRepository;
import com.example.serverTIC.business.company.CompanyRepository;
import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.Club;
import com.example.serverTIC.persistence.Company;
import net.bytebuddy.pool.TypePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final ClubRepository clubRepository;

    private final CompanyRepository companyRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, ClubRepository clubRepository, CompanyRepository companyRepository) {
        this.appUserRepository = appUserRepository;
        this.clubRepository = clubRepository;
        this.companyRepository = companyRepository;
    }


    public ResponseEntity<?> addNewAppUser(AppUser appUser) {
        Optional<AppUser> temp = appUserRepository.findAppUserByEmail(appUser.getEmail());
        if (temp.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        appUserRepository.save(appUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

    public ResponseEntity<?> deleteAppUser(Long id) {
        Optional<AppUser> temp = appUserRepository.findById(id);
        if (temp.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        appUserRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public void updateAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    public ResponseEntity<?> addNewClubUser(AppUser appUser, Long clubId) {
        Optional<Club> temp = clubRepository.findById(clubId);
        if (temp.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Club club = temp.get();
        club.addClubUser(appUser);
        clubRepository.save(club);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> addNewCompanyUser(AppUser appUser, Long companyId) {
        Optional<Company> temp = companyRepository.findById(companyId);
        if (temp.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Company company = temp.get();
        company.addCompanyUser(appUser);
        companyRepository.save(company);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


