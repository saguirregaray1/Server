package com.example.serverTIC.business.appuser;

import com.example.serverTIC.business.club.ClubRepository;
import com.example.serverTIC.business.company.CompanyRepository;
import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.Club;
import com.example.serverTIC.persistence.Company;
import net.bytebuddy.pool.TypePool;
import org.springframework.beans.factory.annotation.Autowired;
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


    public void addNewAppUser(AppUser appUser) {
        Optional<AppUser> temp = appUserRepository.findById(appUser.getId());
        if (temp.isPresent()) {
            throw new IllegalStateException("usuario ya existe");
        }
        appUserRepository.save(appUser);
    }

    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

    public void deleteAppUser(Long id) {
        Optional<AppUser> temp = appUserRepository.findById(id);
        if (temp.isEmpty()) {
            throw new IllegalStateException("club is not registered");
        }
        appUserRepository.deleteById(id);
    }

    public void updateAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    public void addNewClubUser(AppUser appUser, Long clubId) {
        Optional<Club> temp = clubRepository.findById(clubId);
        if (temp.isEmpty()) {
            throw new IllegalStateException("club no existe");
        }
        Club club = temp.get();
        club.addClubUser(appUser);
        clubRepository.save(club);
    }

    public void addNewCompanyUser(AppUser appUser, Long companyId) {
        Optional<Company> temp = companyRepository.findById(companyId);
        if (temp.isEmpty()) {
            throw new IllegalStateException("club no existe");
        }
        Company company = temp.get();
        company.addCompanyUser(appUser);
        companyRepository.save(company);
    }
}


