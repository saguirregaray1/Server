package com.example.serverTIC.persistence;

import javax.persistence.*;
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
    private String dir;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany
    private List<Activity> clubActivities;


    public Club(String nombre, String email, String dir, String password) {
        this.nombre = nombre;
        this.email = email;
        this.dir = dir;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String tel) {
        this.email = tel;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Gym{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono=" + email +
                ", dir='" + dir + '\'' +
                '}';
    }
}