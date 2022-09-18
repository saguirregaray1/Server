package com.example.serverTIC.Business;

import com.example.serverTIC.Persistence.Club;
import com.example.serverTIC.Persistence.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    Optional<Company> findCompanyByNombre(String nombre);


}
