package com.example.serverTIC.business.activity;

import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.ActivityCategories;
import com.example.serverTIC.persistence.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {

    Optional<Activity> findActivitiesByActivityCategories(ActivityCategories activityCategories);

    Optional<Activity> findActivitiesByClubAndNombre(Club club, String nombre);



}
