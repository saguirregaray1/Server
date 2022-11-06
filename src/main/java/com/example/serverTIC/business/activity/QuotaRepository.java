package com.example.serverTIC.business.activity;

import com.example.serverTIC.persistence.Quota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotaRepository extends JpaRepository<Quota,Long> {


}
