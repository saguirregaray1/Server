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

    @JsonBackReference(value = "access")
    @ManyToOne(targetEntity = Employee.class)
    private Employee employee;

    @Column
    private String fecha;

    @JsonBackReference(value = "confirmedUses")
    @ManyToOne(targetEntity = Quota.class)
    private Quota quota;


    public CheckIn(Long checkInId, Employee employee, Quota quota,String fecha) {
        this.checkInId = checkInId;
        this.employee = employee;
        this.quota = quota;
        this.fecha = fecha;
    }

    public CheckIn(Employee employee, Quota quota,String fecha) {
        this.employee = employee;
        this.quota = quota;
        this.fecha = fecha;
    }

    public CheckIn(){}

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getCheckInId() {
        return checkInId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Quota getQuota() {
        return quota;
    }

    public void setCheckInId(Long checkInId) {
        this.checkInId = checkInId;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setQuota(Quota quota) {
        this.quota = quota;
    }
}
