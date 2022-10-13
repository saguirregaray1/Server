package com.example.serverTIC.business.club;

import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club,Long> {

    Optional<Club> findClubByNombre(String nombre);

    @Query("SELECT a.nombre,a.cupos,a.precio,c.nombre,c.dir FROM Activity a INNER JOIN Club c where a.clubId=c.id")
    List<List> getAllActivitiesJoinClubs();



}