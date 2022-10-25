package com.example.serverTIC.persistence;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table
public class Club {
    @Id
    @SequenceGenerator(
            name = "club_sequence",
            sequenceName = "club_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "club_sequence"
    )
    @Column(updatable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nombre;
    @Column(unique = true, nullable = false)
    private String dir;
    @JsonManagedReference(value = "clubActivities")
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Activity> clubActivities;

    @JsonManagedReference(value = "clubUsers")
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<AppUser> clubUsers;


    public Club(String nombre, String dir) {
        this.nombre = nombre;
        this.dir = dir;
        this.clubActivities = new ArrayList<>();
        this.clubUsers = new ArrayList<>();
    }


    public Club() {
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


    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }


    public List<Activity> getClubActivities() {
        return clubActivities;
    }

    public void setClubActivities(List<Activity> clubActivities) {
        this.clubActivities = clubActivities;
    }

    public void addClubUser(AppUser appUser) {
        clubUsers.add(appUser);
    }

    public void addClubActivity(Activity activity) {
        this.clubActivities.add(activity);
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dir='" + dir + '\'' +
                ", clubActivities=" + clubActivities +
                '}';
    }


}