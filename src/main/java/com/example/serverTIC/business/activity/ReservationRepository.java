package com.example.serverTIC.business.activity;

import com.example.serverTIC.persistence.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
