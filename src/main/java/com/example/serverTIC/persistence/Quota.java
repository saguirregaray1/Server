package com.example.serverTIC.persistence;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Quota {

    @Id
    @SequenceGenerator(
            name = "quota_sequence",
            sequenceName = "quota_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "quota_sequence"
    )
    @Column(updatable = false)
    private Long quotaId;

    @Column
    private String day;

    @Column
    private String startTime;

    @Column
    private String finishTime;

    @Column
    private Integer maxCupos;

    @JsonManagedReference(value = "reservationsReceived")
    @OneToMany(mappedBy = "quota", cascade = CascadeType.ALL)
    private List<Reservation> reservationsReceived;

    @JsonManagedReference(value = "confirmedUses")
    @OneToMany(mappedBy = "quota", cascade = CascadeType.ALL)
    private List<CheckIn> confirmedUses;

    @JsonBackReference(value = "cupos")
    @ManyToOne(targetEntity = Activity.class, optional = false)
    private Activity activity;

    public Quota(String day, String startTime, String finishTime, Integer maxCupos, Activity activity, List<Reservation> reservationReceived, List<CheckIn> confirmedUses) {
        this.day = day;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.maxCupos = maxCupos;
        this.activity = activity;
        this.reservationsReceived = reservationReceived;
        this.confirmedUses = confirmedUses;
    }

    public Quota(String day, String startTime, String finishTime, Integer maxCupos, Activity activity) {
        this.day = day;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.maxCupos = maxCupos;
        this.activity = activity;
        this.reservationsReceived = new ArrayList<>();
        this.confirmedUses = new ArrayList<>();
    }

    public Quota(String day, String startTime, String finishTime, Activity activity) {
        this.day = day;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.activity = activity;
        this.maxCupos = -1;
        this.reservationsReceived = new ArrayList<>();
    }

    public Quota(String day, Activity activity) {
        this.day = day;
        this.startTime = null;
        this.finishTime = null;
        this.activity = activity;
        this.maxCupos = -1;
        this.reservationsReceived = new ArrayList<>();
    }


    public Quota() {
    }


    public Long getQuotaId() {
        return quotaId;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public Integer calculateCupos(String fecha) {
        LocalDate now = LocalDate.now();
        Integer cupos = this.maxCupos;
        for (Reservation reservation : this.reservationsReceived) {
            if (reservation.getReservationStatus().equals(ReservationStatus.PENDIENTE) && reservation.getFecha().equals(fecha)) {
                cupos = cupos - 1;
            }
        }
        for (CheckIn checkIn : this.confirmedUses) {
            if (checkIn.getFecha().equals(fecha)) {
                cupos = cupos - 1;
            }
        }
        return cupos;
    }

    public Integer getMaxCupos() {
        return maxCupos;
    }

    public void setMaxCupos(Integer maxCupos) {
        this.maxCupos = maxCupos;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setQuotaId(Long quotaId) {
        this.quotaId = quotaId;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<Reservation> getReservationsReceived() {
        return reservationsReceived;
    }

    public void setReservationsReceived(List<Reservation> reservationsReceived) {
        this.reservationsReceived = reservationsReceived;
    }

    public void addReservation(Reservation reservation){
        this.reservationsReceived.add(reservation);
    }

    public List<CheckIn> getConfirmedUses() {
        return confirmedUses;
    }

    public void setConfirmedUses(List<CheckIn> confirmedUses) {
        this.confirmedUses = confirmedUses;
    }

    public void addCheckIn(CheckIn checkIn){
        this.confirmedUses.add(checkIn);
    }
}
