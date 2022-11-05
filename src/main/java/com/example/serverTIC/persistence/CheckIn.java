package com.example.serverTIC.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table
public class CheckIn {

    @Id
    @SequenceGenerator(
            name = "CheckIn_sequence",
            sequenceName = "CheckIn_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "CheckIn_sequence"
    )
    @Column(updatable = false)
    private Long checkInId;

    @Column
    private String fechaAccedido;

    @JsonBackReference(value = "access")
    @ManyToOne(targetEntity = Employee.class)
    private Employee employee;

    @JsonBackReference(value = "confimedUses")
    @ManyToOne(targetEntity = Activity.class)
    private Activity activity;

    public Long getCheckInId() {
        return checkInId;
    }

    public String getFechaAccedido() {
        return fechaAccedido;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Activity getActivity() {
        return activity;
    }
}
