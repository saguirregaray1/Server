package com.example.serverTIC.business.activity;

import com.example.serverTIC.business.appuser.AppUserRepository;
import com.example.serverTIC.business.club.ClubRepository;
import com.example.serverTIC.business.employee.EmployeeRepository;
import com.example.serverTIC.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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

    public ResponseEntity registerToActivity(Long activityId, Long appUserId, Long scheduleId)/*IMPORTANTE: se agrega argumento para la hora*/ {
        AppUser user = appUserRepository.findById(appUserId).get();
        Optional<Activity> act = activityRepository.findById(activityId);
        Optional<Employee> emp = employeeRepository.findEmployeeById(user.getEmployee().getId());
        if (act.isEmpty() || emp.isEmpty()) {
            return new ResponseEntity<>("actividad o empleado no existen", HttpStatus.BAD_REQUEST);
        }
        Activity activity = act.get();
        Employee employee = emp.get();

        Quota quota = activity.getCupos().get(scheduleId.intValue());

        if (employee.getSaldo() > activity.getPrecio() &&
                quota.getCupos() > 0 &&
                LocalTime.parse(quota.getStartTime()).isBefore(LocalTime.now()) &&
                LocalTime.parse(quota.getFinishTime()).isAfter(LocalTime.now()))
        {
            activity.getCupos().get(scheduleId.intValue()).setCupos(activity.getCupos().get(scheduleId.intValue()).getCupos() - 1);
            employee.setSaldo(employee.getSaldo() - activity.getPrecio());

        } else{
            return new ResponseEntity<>("saldo insuficiente o no hay cupos", HttpStatus.BAD_REQUEST);
        }
        employeeRepository.save(employee);
        activityRepository.save(activity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity cameToActivity(Long activityId, Long cedula) /*IMPORTANTE: se agrega argumento para la hora de la actividad*/ {
        Optional<Activity> act = activityRepository.findById(activityId);
        Optional<Employee> emp = employeeRepository.findEmployeeByCedula(cedula);
        if (act.isEmpty() || emp.isEmpty()) {
            return new ResponseEntity<>("actividad o empleado no existen", HttpStatus.BAD_REQUEST);
        }
        Activity activity = act.get();
        Employee employee = emp.get();

        Quota quota = activity.getCupos().get(LocalDate.now().getDayOfWeek().getValue() - 1);

        if (employee.getSaldo() > activity.getPrecio() &&
                quota.getCupos() == -1 &&
                LocalTime.parse(quota.getStartTime()).isBefore(LocalTime.now()) &&
                LocalTime.parse(quota.getFinishTime()).isAfter(LocalTime.now())) {
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

    public List<List> getActivitiesByClub(Long clubId) {
        Optional<Club> club = clubRepository.findById(clubId);
        if (club.isEmpty()) {
            throw new IllegalStateException("club no existe");
        }
        return clubRepository.getClubActivities(club.get().getClubActivities());
    }

    public List<Quota> getActivityQuota(Long activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        if (activity.isEmpty()){
            throw new IllegalStateException("actividad no existe");
        }
        return activityRepository.getActivityQuota(activityId);
    }
}
