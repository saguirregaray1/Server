package com.example.serverTIC.persistence;

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
    private Long companyId;
    @Column(unique = true, nullable = false)
    private Long cedula;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private Long saldo;
    @Column(nullable = false)
    private String password;
    @OneToMany
    private List<Activity> favs;


    public Employee(Long cedula, String email, Long saldo, Long companyId, String password) {
        this.cedula = cedula;
        this.email = email;
        this.saldo = saldo;
        this.companyId = companyId;
        this.password= password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
}

