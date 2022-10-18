package com.example.serverTIC.business.activity;

import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.ActivityCategories;
import com.example.serverTIC.persistence.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    @Query("select a.club,a.nombre,a.cupos,a.precio,a.activityCategories,c.nombre,c.dir from Activity a Join a.club c where a.activityCategories=:activityCategories")
    List<List> findActivitiesByActivityCategories(@Param("activityCategories") ActivityCategories activityCategories);
    Optional<Activity> findActivitiesByClubIdAndNombre(Long clubId, String nombre);

    @Query("SELECT a.club,a.nombre,a.cupos,a.precio,a.activityCategories,c.nombre,c.dir from Activity a JOIN a.club c")
    List<List> joinClubAndActivity();




}
