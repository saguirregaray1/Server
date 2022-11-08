package com.example.serverTIC.business.activity;

import com.example.serverTIC.business.appuser.AppUserRepository;
import com.example.serverTIC.business.club.ClubRepository;
import com.example.serverTIC.business.employee.EmployeeRepository;
import com.example.serverTIC.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ActivityService {

    private final EmployeeRepository employeeRepository;
    private final ActivityRepository activityRepository;

    private final ClubRepository clubRepository;

    private final AppUserRepository appUserRepository;

    private final QuotaRepository quotaRepository;

    @Autowired
    public ActivityService(EmployeeRepository employeeRepository, ActivityRepository activityRepository, ClubRepository clubRepository, AppUserRepository appUserRepository, QuotaRepository quotaRepository) {
        this.employeeRepository = employeeRepository;
        this.activityRepository = activityRepository;
        this.clubRepository = clubRepository;
        this.appUserRepository = appUserRepository;
        this.quotaRepository = quotaRepository;
    }

    public void addNewActivity(Activity activity) {
        Optional<Club> temp = clubRepository.findById(activity.getClub().getId());
        if (temp.isEmpty()) {
            throw new IllegalStateException("club no existe");
        }
        Club club = temp.get();
        club.addClubActivity(activity);
        clubRepository.save(club);
    }

    public List<List> getActivities() {
        return activityRepository.joinClubAndActivity();
    }

    public void deleteActivity(String activityName, Club club) {

        Optional<Activity> temp = activityRepository.findActivitiesByClubIdAndNombre(club.getId(), activityName);

        if (temp.isEmpty()) {
            throw new IllegalStateException("actividad no existe");
        }
        activityRepository.deleteById(temp.get().getId());
    }

    public List<List> getActivitiesByCategory(String category) {
        ActivityCategories activityCategories = ActivityCategories.valueOf(category);
        return activityRepository.findActivitiesByActivityCategories(activityCategories);
    }

    public ResponseEntity makeReservation(Long appUserId, Long scheduleId, String fecha)/*IMPORTANTE: se agrega argumento para la hora*/ {
        AppUser user = appUserRepository.findById(appUserId).get();
        Optional<Quota> quot= quotaRepository.findById(scheduleId);
        Optional<Employee> emp = employeeRepository.findEmployeeById(user.getEmployee().getId());
        if (quot.isEmpty() || emp.isEmpty()) {
            return new ResponseEntity<>("horario o empleado no existen", HttpStatus.BAD_REQUEST);
        }
        Quota quota = quot.get();
        Employee employee = emp.get();
        Activity activity = quota.getActivity();


        if (employee.getSaldo() > activity.getPrecio() &&
                quota.calculateCupos() > 0)
        {
            Reservation reservation = new Reservation(employee,quota,ReservationStatus.PENDIENTE,fecha);
            employee.addReservation(reservation);
        } else{
            return new ResponseEntity<>("saldo insuficiente o no hay cupos", HttpStatus.BAD_REQUEST);
        }
        employeeRepository.save(employee);
        quotaRepository.save(quota);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity cameToActivityWithoutCupos(Long activityId, Long cedula) /*IMPORTANTE: se agrega argumento para la hora de la actividad*/ {
        Optional<Activity> act = activityRepository.findById(activityId);
        Optional<Employee> emp = employeeRepository.findEmployeeByCedula(cedula);
        if (act.isEmpty() || emp.isEmpty()) {
            return new ResponseEntity<>("actividad o empleado no existen", HttpStatus.BAD_REQUEST);
        }
        Activity activity = act.get();
        Employee employee = emp.get();

        Quota quota = activity.getCupos().get(LocalDate.now().getDayOfWeek().getValue() - 1);

        if (employee.getSaldo() > activity.getPrecio() &&
                quota.getMaxCupos() == -1 &&
                LocalTime.parse(quota.getStartTime()).isBefore(LocalTime.now()) &&
                LocalTime.parse(quota.getFinishTime()).isAfter(LocalTime.now())) {
            employee.setSaldo(employee.getSaldo() - activity.getPrecio());
            CheckIn checkIn = new CheckIn(employee,quota,LocalDate.now().toString());
        }
        employeeRepository.save(employee);
        activityRepository.save(activity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity cameToActivityWithReservation(Long activityId, Long cedula,String startTime) /*IMPORTANTE: se agrega argumento para la hora de la actividad*/ {
        Optional<Activity> act = activityRepository.findById(activityId);
        Optional<Employee> emp = employeeRepository.findEmployeeByCedula(cedula);
        if (act.isEmpty() || emp.isEmpty()) {
            return new ResponseEntity<>("actividad o empleado no existen", HttpStatus.BAD_REQUEST);
        }
        int day = LocalDate.now().getDayOfWeek().getValue();
        Activity activity = act.get();
        Employee employee = emp.get();
        Reservation reservation = null;

        for (Reservation reserva:employee.getReservationsMade()){
            Quota quota = reserva.getQuota();
            if (DayOfWeek.valueOf(quota.getDay()).getValue()==day && quota.getStartTime().equals(startTime) && reserva.getReservationStatus().equals(ReservationStatus.PENDIENTE)){
                reservation = reserva;
            }
        }

        if (Objects.isNull(reservation)){
            throw new IllegalStateException("reservacion no existe");
        }

        if (employee.getSaldo() > activity.getPrecio()) {
            employee.setSaldo(employee.getSaldo() - activity.getPrecio());
            CheckIn checkIn = new CheckIn(employee, reservation.getQuota(),LocalDate.now().toString());
            reservation.setReservationStatus(ReservationStatus.ATENDIDO);
            employee.addAccess(checkIn);
        }
        employeeRepository.save(employee);
        activityRepository.save(activity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity cameToActivityWithNoReservation(Long activityId, Long cedula,String startTime) /*IMPORTANTE: se agrega argumento para la hora de la actividad*/ {
        Optional<Activity> act = activityRepository.findById(activityId);
        Optional<Employee> emp = employeeRepository.findEmployeeByCedula(cedula);
        int day = LocalDate.now().getDayOfWeek().getValue();
        if (act.isEmpty() || emp.isEmpty()) {
            return new ResponseEntity<>("actividad o empleado no existen", HttpStatus.BAD_REQUEST);
        }
        Activity activity = act.get();
        Employee employee = emp.get();
        Quota quota = null;

        for (Quota quot:activity.getCupos()){
             ;
            if (DayOfWeek.valueOf(quot.getDay()).getValue()==day && quot.getStartTime().equals(startTime)){
                quota=quot;
                if (quota.getMaxCupos()==-1){
                    return cameToActivityWithoutCupos(activityId,cedula);
                }
            }
        }

        if (Objects.isNull(quota)){
            throw new IllegalStateException("horario no encontrado");
        }

        if (employee.getSaldo() > activity.getPrecio() && quota.calculateCupos()>0) {
            employee.setSaldo(employee.getSaldo() - activity.getPrecio());
            CheckIn checkIn = new CheckIn(employee, quota,LocalDate.now().toString());
            employee.addAccess(checkIn);
        }
        employeeRepository.save(employee);
        activityRepository.save(activity);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public void updateActivity(Activity activity) {
        Optional<Club> temp = clubRepository.findById(activity.getClub().getId());
        if (temp.isEmpty()) {
            throw new IllegalStateException("club no existe");
        }
        Club club = temp.get();
        club.addClubActivity(activity);
        clubRepository.save(club);
    }

    public List<List> getActivitiesByClub(Long clubId) {
        Optional<Club> club = clubRepository.findById(clubId);
        if (club.isEmpty()) {
            throw new IllegalStateException("club no existe");
        }
        return clubRepository.getClubActivities(club.get().getClubActivities());
    }

    public List<Quota> getActivityQuota(Long activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        if (activity.isEmpty()){
            throw new IllegalStateException("actividad no existe");
        }
        return activityRepository.getActivityQuota(activityId);
    }

    public ResponseEntity getActivityByNombre(String clubId,String nombre){
        Optional<Activity> act= activityRepository.findActivitiesByClubIdAndNombre(Long.parseLong(clubId),nombre);
        if (act.isEmpty()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(act.get(),HttpStatus.OK);
    }
}
