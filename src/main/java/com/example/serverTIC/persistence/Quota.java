package com.example.serverTIC.persistence;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

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
    private Integer cupos;

    @JsonBackReference(value = "cupos")
    @ManyToOne(targetEntity = Activity.class, optional = false)
    private Activity activity;

    public Quota(String day, String startTime, String finishTime, Integer cupos, Activity activity) {
        this.day = day;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.cupos = cupos;
        this.activity = activity;
    }

    public Quota(String day, String startTime, String finishTime, Activity activity) {
        this.day = day;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.activity = activity;
        this.cupos = -1;
    }

    public Quota(String day,Activity activity) {
        this.day = day;
        this.startTime = null;
        this.finishTime = null;
        this.activity = activity;
        this.cupos = -1;
    }


    public Quota(){
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

    public Integer getCupos() {
        return cupos;
    }

    public void setCupos(Integer cupos) {
        this.cupos = cupos;
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
}
