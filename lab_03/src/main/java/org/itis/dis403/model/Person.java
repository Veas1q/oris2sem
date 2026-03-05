package org.itis.dis403.model;

import jakarta.persistence.*;

import static jakarta.persistence.InheritanceType.JOINED;

//@Getter@Setter
@Entity
@Inheritance(strategy = JOINED)
class Person {

    @Id
    protected Long id;

    protected String name;

    public Long getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}