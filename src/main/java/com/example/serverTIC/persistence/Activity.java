package com.example.serverTIC.persistence;


import javax.persistence.*;


@Entity
@Table
public class Activity {

    @Id
    @SequenceGenerator(
            name = "company_sequence",
            sequenceName = "company_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_sequence"
    )

    private Long id;

    private String clubId;

    private String nombre;
    private Long precio;

    private int cupos;

    private int categoria;

    public Activity(String nombre,Long precio, int cupos, int categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.cupos = cupos;
        this.categoria = categoria;
    }

    public Activity() {
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
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