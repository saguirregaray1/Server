package com.example.serverTIC.business.appuser;

import com.example.serverTIC.persistence.AppUser;
import com.example.serverTIC.persistence.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findAppUserByEmail(String email);
}
