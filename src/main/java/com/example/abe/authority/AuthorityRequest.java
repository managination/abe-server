package com.example.abe.authority;

public class AuthorityRequest {
    private String name;
    private String[] attributes;

    public AuthorityRequest(String name, String[] attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }

}
