package com.example.serverTIC.business.employee;

import com.example.serverTIC.persistence.Activity;
import com.example.serverTIC.persistence.Company;
import com.example.serverTIC.persistence.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findEmployeeByCedula(Long cedula);

    Optional<Employee> findEmployeeById(Long employeeId);

    @Query("select a.nombre,a.precio,a.activityCategories,c.nombre,c.dir,a.id from Activity a Join a.club c where a in :favouriteList")
    List<List> getFavouriteList(@Param("favouriteList") List<Activity> favouriteList);


    @Query("select e from Employee e where e.company=:company")
    List<Employee> findEmployeeByCompanyId(@Param("company") Company company);
}
