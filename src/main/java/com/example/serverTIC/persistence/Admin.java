package com.example.serverTIC.persistence;

import javax.persistence.*;

@Entity(name = "Admin")
@Table(name = "Admin_users")
public class Admin {
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
    @Column(name = "admin_id", updatable = false)
    private Long id;
    @Column(name = "email", unique = true, nullable = false)
    private String mail;
    @Column(name = "password", nullable = false)
    private String password;

    public Admin(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public Admin(){
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
