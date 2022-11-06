package com.example.serverTIC.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table
public class Reservation {

    @Id
    @SequenceGenerator(
            name = "Reservation_sequence",
            sequenceName = "Reservation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Reservation_sequence"
    )
    @Column(updatable = false)
    private Long ReservationId;

    @Column
    private String fecha;


    @JsonBackReference(value = "reservationsMade")
    @ManyToOne(targetEntity = Employee.class)
    private Employee employee;

    @JsonBackReference(value = "reservationsReceived")
    @ManyToOne(targetEntity = Quota.class)
    private Quota quota;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;


    public Reservation() { }

    public Reservation(Employee employee, Quota quota,ReservationStatus reservationStatus,String fecha) {
        this.employee = employee;
        this.quota = quota;
        this.reservationStatus = reservationStatus;
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getReservationId() {
        return ReservationId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Quota getQuota() {
        return quota;
    }

    public void setReservationId(Long reservationId) {
        ReservationId = reservationId;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setQuota(Quota quota) {
        this.quota = quota;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
