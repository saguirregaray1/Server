package com.example.serverTIC.business.activity;

import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
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
}
