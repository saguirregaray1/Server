package com.example.serverTIC.business.activity;

import com.example.serverTIC.persistence.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {

    Optional<Activity> findActivityByNombre(String nombre);

    Optional<Activity> findActivitiesByCategoria(int categoria);

    Optional<Activity> findActivityById(Long id);


}
