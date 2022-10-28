package com.example.serverTIC.persistence;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table
public class Quota {

    @Id
    @SequenceGenerator(
            name = "activity_sequence",
            sequenceName = "activity_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "activity_sequence"
    )
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
}
