package com.example.serverTIC.business.employee;

import com.example.serverTIC.persistence.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findEmployeeByCedula(Long cedula);

    Optional<Employee> findEmployeeById(Long employeeId);


}
