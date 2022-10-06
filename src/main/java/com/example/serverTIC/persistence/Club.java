package com.example.serverTIC.persistence;

import javax.persistence.*;

@Entity(name = "Club")
@Table(name = "club")
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
    @Column(name = "club_id", updatable = false)
    private Long id;
    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;
    private String dir;
    @Column(name = "email", unique = true, nullable = false)
    private String mail;
    @Column(name = "password", nullable = false)
    private String password;


    public Club(String nombre, String mail, String dir, String password) {
        this.nombre = nombre;
        this.mail = mail;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String tel) {
        this.mail = tel;
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
                ", telefono=" + mail +
                ", dir='" + dir + '\'' +
                '}';
    }
}