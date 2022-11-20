package com.example.serverTIC.business.activity;

import com.example.serverTIC.persistence.CheckIn;
import com.example.serverTIC.persistence.Employee;
import com.example.serverTIC.persistence.Quota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuotaRepository extends JpaRepository<Quota,Long> {

}
