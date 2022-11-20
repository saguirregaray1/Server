package com.example.serverTIC.business.employee;

import com.example.serverTIC.business.activity.ActivityRepository;
import com.example.serverTIC.business.activity.ReservationRepository;
import com.example.serverTIC.business.appuser.AppUserRepository;
import com.example.serverTIC.business.company.CompanyRepository;
import com.example.serverTIC.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService{

    private final EmployeeRepository employeeRepository;

    private final ActivityRepository activityRepository;

    private final AppUserRepository appUserRepository;

    private final CompanyRepository companyRepository;

    private final ReservationRepository reservationRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, AppUserRepository appUserRepository, ActivityRepository activityRepository, CompanyRepository companyRepository, ReservationRepository reservationRepository){
        this.employeeRepository = employeeRepository;
        this.appUserRepository = appUserRepository;
        this.activityRepository = activityRepository;
        this.companyRepository = companyRepository;
        this.reservationRepository = reservationRepository;
    }

    public ResponseEntity<?> addNewEmployee(Employee employee){
        Optional<Company> temp=companyRepository.findById(employee.getCompany().getId());
        if(temp.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Company company = temp.get();
        company.addEmployee(employee);
        companyRepository.save(company);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Transactional
    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
        }

    public ResponseEntity<?> deleteEmployee(Long cedulaEmp) {
        Optional<Employee> temp=employeeRepository.findEmployeeByCedula(cedulaEmp);
        if(temp.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //borrar de compañia
        employeeRepository.deleteById(temp.get().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<Employee> isAnEmployee(Long cedula) {
        Optional<Employee> emp = employeeRepository.findEmployeeByCedula(cedula);
        if (emp.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(emp.get(),HttpStatus.OK);
    }


    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public ResponseEntity<?> addFavouriteActivity(Long activityId, AppUser appUser){
        AppUser user=appUserRepository.findById(appUser.getId()).get();
        Optional<Employee> emp=employeeRepository.findById(user.getEmployee().getId());
        Optional<Activity> act=activityRepository.findById(activityId);
        if(emp.isEmpty() || act.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Employee employee=emp.get();
        Activity activity=act.get();
        if (!employee.getFavs().contains(activity)){
            employee.addFav(activity);
        }
        employeeRepository.save(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @Scheduled(cron = "0 4 1 * *")
    public void changeExpiredReservationsStatus(){
        List<Reservation> reservations = reservationRepository.getAllByReservationStatus(ReservationStatus.PENDIENTE);
        for (Reservation reservation: reservations){
            if (LocalDate.parse(reservation.getFecha()).isBefore(LocalDate.now())){
                reservation.setReservationStatus(ReservationStatus.NO_ATENDIDO);
                reservationRepository.save(reservation);
            }
        }
    }

    public List<List> getFavsList(Long userId) {
        AppUser appUser=appUserRepository.findById(userId).get();
        Optional<Employee> temp=employeeRepository.findById(appUser.getEmployee().getId());
        if(temp.isEmpty()){
            return new ArrayList<>();
        }
        return employeeRepository.getFavouriteList(temp.get().getFavs());
    }

    public List<List> getPendingReservations(Long userId) {
        AppUser appUser=appUserRepository.findById(userId).get();
        Optional<Employee> temp=employeeRepository.findById(appUser.getEmployee().getId());
        if(temp.isEmpty()){
            return new ArrayList<>();
        }
        return employeeRepository.findReservationsById(temp.get().getId());
    }

    public List<Quota> getPendingReservationsUtil(Long userId) {
        AppUser appUser=appUserRepository.findById(userId).get();
        Optional<Employee> temp=employeeRepository.findById(appUser.getEmployee().getId());
        if(temp.isEmpty()){
            return new ArrayList<>();
        }
        return employeeRepository.findQuotaById(temp.get().getId());
    }

    public List<Activity> getPendingReservationsUtil2(Long userId) {
        AppUser appUser=appUserRepository.findById(userId).get();
        Optional<Employee> temp=employeeRepository.findById(appUser.getEmployee().getId());
        if(temp.isEmpty()){
            return new ArrayList<>();
        }
        return employeeRepository.findActivitiesById(temp.get().getId());
    }
}
