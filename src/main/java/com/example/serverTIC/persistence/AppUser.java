package com.example.serverTIC.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.LazyToOne;

import javax.persistence.*;

@Entity
@Table
public class AppUser {
    @Id
    @SequenceGenerator(
            name = "appuser_sequence",
            sequenceName = "appuser_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appuser_sequence"
    )
    @Column(updatable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    @JsonBackReference (value = "employeeUser")
    @OneToOne (targetEntity = Employee.class)
    private Employee employee;

    @JsonBackReference (value = "adminUser")
    @OneToOne (targetEntity = Admin.class)
    private Admin admin;

    @JsonBackReference (value = "clubUsers")
    @ManyToOne (targetEntity = Club.class)
    private Club club;

    @JsonBackReference (value = "companyUsers")
    @ManyToOne (targetEntity = Company.class)
    private Company company;

    public AppUser(String email, String password, AppUserRole appUserRole, Employee employee) {
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.employee = employee;
    }
    public AppUser(String email, String password, AppUserRole appUserRole, Club club) {
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.club = club;
    }

    public AppUser(String email, String password, AppUserRole appUserRole, Company company) {
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.company = company;
    }

    public AppUser(String email, String password, AppUserRole appUserRole, Admin admin) {
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.admin = admin;
    }

    public AppUserRole getAppUserRole() {
        return appUserRole;
    }

    public void setAppUserRole(AppUserRole appUserRole) {
        this.appUserRole = appUserRole;
    }


    public AppUser(){
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        if (appUserRole.equals(AppUserRole.EMPLOYEE)){
        return "AppUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", appUserRole=" + appUserRole +
                ", employee=" + employee.getId() +
                '}';
    } else if (appUserRole.equals(AppUserRole.COMPANY_USER)) {
            return "AppUser{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", appUserRole=" + appUserRole +
                    ", company=" + company.getId() +
                    '}';
        } else if (appUserRole.equals(AppUserRole.CLUB_USER)) {
            return "AppUser{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", appUserRole=" + appUserRole +
                    ", club=" + club.getId() +
                    '}';

        }
        else{
            return "AppUser{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", appUserRole=" + appUserRole +
                    ", admin=" + admin.getId() +
                    '}';
        }
    }
}
