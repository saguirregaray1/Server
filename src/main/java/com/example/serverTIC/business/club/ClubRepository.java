package com.example.serverTIC.business.club;

import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.Club;
import com.example.serverTIC.persistence.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club,Long> {

    Optional<Club> findClubByNombre(String nombre);

    @Query("select a.nombre,a.precio,a.activityCategories,c.nombre,c.dir,a.id from Activity a Join a.club c where a.club=:club")
    List<List> getClubActivities(@Param("club") Club club);

}