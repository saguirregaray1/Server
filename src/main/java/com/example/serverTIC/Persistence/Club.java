package com.example.serverTIC.Persistence;

import javax.persistence.*;

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
    private Long id;
    private String nombre;
    private String tel;
    private String dir;


    public Club(String nombre, String tel, String dir) {
        this.nombre = nombre;
        this.tel = tel;
        this.dir = dir;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "Gym{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono=" + tel +
                ", dir='" + dir + '\'' +
                '}';
    }
}