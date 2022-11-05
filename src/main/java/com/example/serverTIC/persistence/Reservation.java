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
    private String fechaReservada;

    @JsonBackReference(value = "reservationsMade")
    @ManyToOne(targetEntity = Employee.class)
    private Employee employee;

    @JsonBackReference(value = "reservationsReceived")
    @ManyToOne(targetEntity = Activity.class)
    private Activity activity;


    public Reservation(){

    }

    public Reservation(String fechaReservada, Employee employee, Activity activity) {
        this.fechaReservada = fechaReservada;
        this.employee = employee;
        this.activity = activity;
    }

    public Long getReservationId() {
        return ReservationId;
    }

    public String getFechaReservada() {
        return fechaReservada;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Activity getActivity() {
        return activity;
    }
}
