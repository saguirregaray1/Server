package com.example.serverTIC.persistence;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
public class Activity {

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
    @Column(name = "activity_id", updatable = false)
    private Long id;

    @JsonBackReference (value = "clubActivities")
    @ManyToOne (targetEntity = Club.class)
    private Club club;
    @Column(unique = true, nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Long precio;
    @Column
    private Integer cupos;

    @ManyToMany
    @JoinTable(name = "Activity_Image",joinColumns = {@JoinColumn(name = "Activity_Id")},
            inverseJoinColumns = {@JoinColumn(name = "Image_Id")})
    private List<Image> pictures;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityCategories activityCategories;

    public Activity(Club club, String nombre, Long precio, Integer cupos, ActivityCategories activityCategories) {
        this.club = club;
        this.nombre = nombre;
        this.precio = precio;
        this.cupos = cupos;
        this.activityCategories = activityCategories;
        this.pictures = new ArrayList<Image>();
    }

    public Activity() {
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public Integer getCupos() {
        return cupos;
    }

    public void setCupos(Integer cupos) {
        this.cupos = cupos;
    }

    public ActivityCategories getActivityCategories() {
        return activityCategories;
    }

    public void setActivityCategories(ActivityCategories activityCategories) {
        this.activityCategories = activityCategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
