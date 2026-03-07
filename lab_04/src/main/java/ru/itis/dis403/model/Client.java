package ru.itis.dis403.model;

import jakarta.persistence.Entity;

//@Getter@Setter
@Entity
public class Client extends Person {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}