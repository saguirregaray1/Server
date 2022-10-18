package com.example.serverTIC.business.employee;

import com.example.serverTIC.persistence.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findEmployeeByCedula(Long cedula);

    Optional<Employee> findEmployeeById(Long employeeId);

    @Query("select a.club,a.nombre,a.cupos,a.precio,a.activityCategories,c.nombre,c.dir from  a Join")
    List<List> getFavouriteList(Long employeeId);
}
