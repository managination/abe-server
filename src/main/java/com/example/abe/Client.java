package com.example.abe;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

//@Entity(name = "Client")
//@Table(
//        name = "client",
//        uniqueConstraints = {
//                @UniqueConstraint(name = "client_email_unique", columnNames = "email")
//        }
//)
@Entity
@Table
public class Client {

    @Id
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "client_sequence"
    )
//    @Column(
//            name = "id",
//            updatable = false
//    )
    private Long id;

//    @Column(
//            name = "name",
//            nullable = false,
//            columnDefinition = "TEXT"
//    )
    private String name;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    public Client() {
    }

    public Client(String name,
                  String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
