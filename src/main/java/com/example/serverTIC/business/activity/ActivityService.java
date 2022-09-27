package com.example.serverTIC.business.activity;

import com.example.serverTIC.business.employee.EmployeeRepository;
import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final EmployeeRepository employeeRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(EmployeeRepository employeeRepository, ActivityRepository activityRepository) {
        this.employeeRepository = employeeRepository;
        this.activityRepository = activityRepository;
    }

    public void addNewActivity(Activity activity) {
        activityRepository.save(activity);
    }

    public List<Activity> getActivities(){
        return activityRepository.findAll();
    }

    public void deleteActivity(String activityName) {
        Optional<Activity> temp= activityRepository.findActivityByNombre(activityName);
        if(temp.isEmpty()){
            throw new IllegalStateException("club is not registered");
        }
        activityRepository.deleteById(temp.get().getId());
    }

    public Optional<Activity> getActivitiesByCategory(int category){
        return activityRepository.findActivitiesByCategoria(category);
    }

    public boolean registerToActivity(Long activityId, Long employeeId) {
        Optional<Activity> act= activityRepository.findActivityById(activityId);
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
}