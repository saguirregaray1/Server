package com.example.serverTIC.business.activity;

import com.example.serverTIC.business.club.ClubRepository;
import com.example.serverTIC.business.employee.EmployeeRepository;
import com.example.serverTIC.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final EmployeeRepository employeeRepository;
    private final ActivityRepository activityRepository;

    private final ClubRepository clubRepository;

    @Autowired
    public ActivityService(EmployeeRepository employeeRepository, ActivityRepository activityRepository, ClubRepository clubRepository) {
        this.employeeRepository = employeeRepository;
        this.activityRepository = activityRepository;
        this.clubRepository = clubRepository;
    }

    public void addNewActivity(Activity activity) {
        Optional<Club> temp=clubRepository.findById(activity.getClubId());
        if(temp.isEmpty()){
            throw new IllegalStateException("club no existe");
        }
        Club club=temp.get();
        activityRepository.save(activity);
        club.addClubActivity(activity);
        clubRepository.save(club);


    }

    public List<Activity> getActivities(){
        return activityRepository.findAll();
    }

    public void deleteActivity(String activityName, Club club) {

        Optional<Activity> temp=activityRepository.findActivitiesByClubIdAndNombre(club.getId(),activityName);

        if (temp.isEmpty()){
            throw new IllegalStateException("actividad no existe");
        }
        activityRepository.deleteById(temp.get().getId());
        //borrar de club activity

    }

    public List<Activity> getActivitiesByCategory(ActivityCategories category){
        return activityRepository.findActivitiesByActivityCategories(category);
    }

    public boolean registerToActivity(Long activityId, Long employeeId) {
        Optional<Activity> act= activityRepository.findById(activityId);
        Optional<Employee> emp= employeeRepository.findEmployeeById(employeeId);
        if(act.isEmpty() || emp.isEmpty()){
            throw new IllegalStateException("activity doesn't exist");
        }
        Activity activity=act.get();
        Employee employee=emp.get();
        if(activity.getCupos()==0 || employee.getSaldo()<activity.getPrecio()) {
            return false;
        }
        activity.setCupos(activity.getCupos()-1);
        employee.setSaldo(employee.getSaldo()-activity.getPrecio());
        return true;
    }

    public void updateActivity(Activity activity, Long activityId) {
        Optional<Activity> temp=activityRepository.findById(activityId);
        if(temp.isEmpty()){
            addNewActivity(activity);
        }
        else{
            Activity activity1=temp.get();
            activity1.setPrecio(activity.getPrecio());
            activity1.setNombre(activity.getNombre());
            activity1.setCupos(activity.getCupos());
            activity1.setActivityCategories(activity.getActivityCategories());
            activityRepository.save(activity);
            //update en el club
        }
    }

}
