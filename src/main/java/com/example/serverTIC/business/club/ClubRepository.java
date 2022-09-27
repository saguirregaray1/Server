package com.example.serverTIC.business.club;

import com.example.serverTIC.persistence.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club,Long> {

    Optional<Club> findClubByNombre(String nombre);



}