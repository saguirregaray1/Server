package com.example.serverTIC.business.activity;

import com.example.serverTIC.persistence.Reservation;
import com.example.serverTIC.persistence.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> getAllByReservationStatus(ReservationStatus reservationStatus);

}
