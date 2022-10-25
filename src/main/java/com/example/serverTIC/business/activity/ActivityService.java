package com.example.serverTIC.business.activity;

import com.example.serverTIC.business.appuser.AppUserRepository;
import com.example.serverTIC.business.club.ClubRepository;
import com.example.serverTIC.business.employee.EmployeeRepository;
import com.example.serverTIC.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ActivityService {

    private final EmployeeRepository employeeRepository;
    private final ActivityRepository activityRepository;

    private final ClubRepository clubRepository;

    private final AppUserRepository appUserRepository;

    @Autowired
    public ActivityService(EmployeeRepository employeeRepository, ActivityRepository activityRepository, ClubRepository clubRepository, AppUserRepository appUserRepository) {
        this.employeeRepository = employeeRepository;
        this.activityRepository = activityRepository;
        this.clubRepository = clubRepository;
        this.appUserRepository = appUserRepository;
    }

    public void addNewActivity(Activity activity) {
        Optional<Club> temp = clubRepository.findById(activity.getClub().getId());
        if (temp.isEmpty()) {
            throw new IllegalStateException("club no existe");
        }
        Club club = temp.get();
        activityRepository.save(activity);
        club.addClubActivity(activity);
        clubRepository.save(club);
    }

    public List<List> getActivities() {
        return activityRepository.joinClubAndActivity();
    }

    public void deleteActivity(String activityName, Club club) {

        Optional<Activity> temp = activityRepository.findActivitiesByClubIdAndNombre(club.getId(), activityName);

        if (temp.isEmpty()) {
            throw new IllegalStateException("actividad no existe");
        }
        activityRepository.deleteById(temp.get().getId());
        //borrar de club activity

    }

    public List<List> getActivitiesByCategory(String category) {
        ActivityCategories activityCategories = ActivityCategories.valueOf(category);
        return activityRepository.findActivitiesByActivityCategories(activityCategories);
    }

    public ResponseEntity registerToActivity(Long activityId, AppUser appUser) {
        AppUser user = appUserRepository.findById(appUser.getId()).get();
        Optional<Activity> act = activityRepository.findById(activityId);
        Optional<Employee> emp = employeeRepository.findEmployeeById(user.getEmployee().getId());
        if (act.isEmpty() || emp.isEmpty()) {
            throw new IllegalStateException("activity doesn't exist");
        }
        Activity activity = act.get();
        Employee employee = emp.get();

        if (Objects.isNull(activity.getCupos())) {
            employee.setSaldo(employee.getSaldo() - activity.getPrecio());
        } else {
            if (activity.getCupos() == 0 || employee.getSaldo() < activity.getPrecio()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            activity.setCupos(activity.getCupos() - 1);
            employee.setSaldo(employee.getSaldo() - activity.getPrecio());
        }
        employeeRepository.save(employee);
        activityRepository.save(activity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void updateActivity(Activity activity) {
        Optional<Club> temp = clubRepository.findById(activity.getClub().getId());
        if (temp.isEmpty()) {
            throw new IllegalStateException("club no existe");
        }
        Club club = temp.get();
        club.addClubActivity(activity);
        clubRepository.save(club);
    }

}
