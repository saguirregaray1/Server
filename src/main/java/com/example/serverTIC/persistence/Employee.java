package com.example.serverTIC.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Employee {
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    @Column(updatable = false)
    private Long id;
    @JsonBackReference
    @ManyToOne (targetEntity = Company.class)
    private Company company;
    @Column(unique = true, nullable = false)
    private Long cedula;
    @Column(nullable = false)
    private Long saldo;
    @Column
    private String email;
    @Column
    private String password;
    @OneToMany
    private List<Activity> favs;


    public Employee(Company company,Long cedula,Long saldo,String email,String password, ArrayList<Activity> favs) {
        this.cedula = cedula;
        this.saldo = saldo;
        this.company = company;
        this.email = email;
        this.password = password;
        this.favs = favs;
    }

    public Employee(Company company,Long cedula,Long saldo,String email,String password) {
        this.cedula = cedula;
        this.saldo = saldo;
        this.company = company;
        this.email = email;
        this.password = password;
        this.favs = new ArrayList<>();
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Activity> getFavs() {
        return favs;
    }

    public void setFavs(List<Activity> favs) {
        this.favs = favs;
    }

    public void addFav(Activity activity) {
        this.favs.add(activity);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", company=" + company +
                ", cedula=" + cedula +
                ", saldo=" + saldo +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", favs=" + favs +
                '}';
    }
}

