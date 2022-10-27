package com.example.serverTIC.persistence;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Company {

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
    @Column(updatable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Long nroCuenta;
    @JsonManagedReference(value = "companyEmployees")
    @OneToMany(mappedBy = "company", cascade = {CascadeType.ALL})
    private List<Employee> companyEmployees;

    @JsonManagedReference(value = "companyUsers")
    @OneToMany(mappedBy = "company", cascade = {CascadeType.ALL})
    private List<AppUser> companyUsers;


    public Company(String nombre, Long nroCuenta) {
        this.nombre = nombre;
        this.nroCuenta = nroCuenta;
        this.companyEmployees = new ArrayList<>();
    }

    public Company() {
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

    public Long getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(Long nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public List<Employee> getCompanyEmployees() {
        return companyEmployees;
    }

    public void setCompanyEmployees(List<Employee> companyEmployees) {
        this.companyEmployees = companyEmployees;
    }

    public void addEmployee(Employee employee) {
        this.companyEmployees.add(employee);
    }

    public void addCompanyUser(AppUser appUser) {
        companyUsers.add(appUser);
    }

    public List<AppUser> getCompanyUsers() {
        return companyUsers;
    }

    public void setCompanyUsers(List<AppUser> companyUsers) {
        this.companyUsers = companyUsers;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nroCuenta=" + nroCuenta +
                ", companyEmployees=" + companyEmployees +
                '}';
    }


}
