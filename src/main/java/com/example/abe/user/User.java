package com.example.abe.user;

import java.util.List;

public class User {
    private Long id;
    private String name;
    private String email;
    private List<String> attributes;

    public User(Long id,
                String name,
                String email,
                List<String> attributes) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.attributes = attributes;
    }

    public User(String name,
                String email,
                List<String> attributes) {
        this.name = name;
        this.email = email;
        this.attributes = attributes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
