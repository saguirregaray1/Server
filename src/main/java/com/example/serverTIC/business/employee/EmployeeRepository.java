package com.example.serverTIC.business.employee;

import com.example.serverTIC.persistence.*;
import org.hibernate.annotations.Check;
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

    @Query("select a.quota.day, a.quota.startTime, a.quota.activity.nombre from Employee e join e.reservationsMade a where e.id=:employeeId and a.reservationStatus='PENDIENTE'")
    List<List> findReservationsById(@Param("employeeId")Long employeeId);
    @Query("select a.quota from Employee e join e.reservationsMade a where e.id=:employeeId and a.reservationStatus='PENDIENTE'")
    List<Quota> findQuotaById(@Param("employeeId") Long employeeId);
    @Query("select a.quota.activity from Employee e join e.reservationsMade a where e.id=:employeeId and a.reservationStatus='PENDIENTE'")
    List<Activity> findActivitiesById(@Param("employeeId") Long employeeId);


    @Query("select a.nombre,a.precio,a.activityCategories,c.nombre,c.dir,a.id from Activity a Join a.club c where a in :favouriteList")
    List<List> getFavouriteList(@Param("favouriteList") List<Activity> favouriteList);


    @Query("select e from Employee e where e.company=:company")
    List<Employee> findEmployeeByCompanyId(@Param("company") Company company);


    //fixme
    @Query("SELECT sum(a.quota.activity.precio) from CheckIn a where a.employee=:employee and a.fecha like concat(:fecha,'-__')")
    Optional<Long> findCheckInsCost(@Param("employee")Employee employee,@Param("fecha") String fecha);

    @Query("SELECT a from CheckIn a where a.employee=:employee and a.fecha like concat(:fecha,'-__')")
    List<CheckIn> findCheckInList(@Param("employee")Employee employee,@Param("fecha") String fecha);


}
