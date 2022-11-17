package com.example.serverTIC.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Employee {
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    @Column(name = "employee_id", updatable = false)
    private Long id;
    @JsonBackReference (value = "companyEmployees")
    @ManyToOne(targetEntity = Company.class)
    private Company company;
    @Column(unique = true, nullable = false)
    private Long cedula;
    @Column(nullable = false)
    private Long saldo;
    @JsonManagedReference (value = "employeeUser")
    @OneToOne(mappedBy = "employee", cascade = {CascadeType.ALL})
    private AppUser appUser;

    @JsonManagedReference(value = "access")
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<CheckIn> access;

    @JsonManagedReference(value = "reservationsMade")
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Reservation> reservationsMade;


    @ManyToMany
    @JoinColumn(name = "activity_id")
    private List<Activity> favs;


    public Employee(Company company, Long cedula, Long saldo, AppUser appUser, ArrayList<Activity> favs) {
        this.cedula = cedula;
        this.saldo = saldo;
        this.company = company;
        this.appUser = appUser;
        this.favs = favs;
    }

    public Employee(Company company, Long cedula, Long saldo, AppUser appUser) {
        this.cedula = cedula;
        this.saldo = saldo;
        this.company = company;
        this.appUser = appUser;
        this.favs = new ArrayList<>();
    }

    public Employee(Company company, Long cedula, Long saldo, ArrayList<Activity> favs) {
        this.cedula = cedula;
        this.saldo = saldo;
        this.company = company;
        this.appUser = new AppUser();
        this.favs = favs;
    }

    public Employee(Company company, Long cedula, Long saldo) {
        this.cedula = cedula;
        this.saldo = saldo;
        this.company = company;
        this.appUser = new AppUser();
        this.favs = new ArrayList<>();
    }

    public Employee() {
    }

    public List<Reservation> getReservationsMade() {
        return reservationsMade;
    }

    public List<CheckIn> getAccess() {
        return access;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public List<Activity> getFavs() {
        return favs;
    }

    public void setFavs(List<Activity> favs) {
        this.favs = favs;
    }

    public void addFav(Activity activity) {
        this.favs.add(activity);
    }

    public void setAccess(List<CheckIn> access) {
        this.access = access;
    }

    public void setReservationsMade(List<Reservation> reservationsMade) {
        this.reservationsMade = reservationsMade;
    }

    public void addReservation(Reservation reservation){
        this.reservationsMade.add(reservation);
    }

    public void addAccess(CheckIn checkIn){
        this.access.add(checkIn);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", cedula=" + cedula +
                '}';
    }

    public boolean hasReservation(String fechaReserva, Long quotaId) {
        for (Reservation reservation:reservationsMade){
            if (reservation.getQuota().getQuotaId().equals(quotaId) && reservation.getFecha().equals(fechaReserva)
                    &&reservation.getReservationStatus().equals(ReservationStatus.PENDIENTE)){
                return true;
            }
        }
        return false;
    }

    public boolean cancelReservation(String fechaReserva, Long quotaId){
        for (Reservation reservation:reservationsMade){
            if (reservation.getQuota().getQuotaId().equals(quotaId) && reservation.getFecha().equals(fechaReserva)
                    &&reservation.getReservationStatus().equals(ReservationStatus.PENDIENTE)){
                reservationsMade.remove(reservation);
                return true;
            }
        }
        return false;
    }
}
