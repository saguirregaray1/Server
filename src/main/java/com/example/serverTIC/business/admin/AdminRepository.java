package com.example.serverTIC.business.admin;

import com.example.serverTIC.persistence.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findAdminByMail(String mail);
}
