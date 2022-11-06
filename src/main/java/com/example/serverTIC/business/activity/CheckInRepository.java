package com.example.serverTIC.business.activity;

import com.example.serverTIC.persistence.CheckIn;
import org.hibernate.annotations.Check;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn,Long> {
}
