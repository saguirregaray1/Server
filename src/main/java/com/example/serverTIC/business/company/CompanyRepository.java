package com.example.serverTIC.business.company;

import com.example.serverTIC.persistence.Company;
import com.example.serverTIC.persistence.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    Optional<Company> findCompanyByNombre(String nombre);
}
