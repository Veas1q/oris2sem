package ru.itis.dis403.lab_02.orm.model;

import ru.itis.dis403.lab_02.orm.annotation.Column;
import ru.itis.dis403.lab_02.orm.annotation.Entity;
import ru.itis.dis403.lab_02.orm.annotation.Id;
import ru.itis.dis403.lab_02.orm.annotation.ManyToOne;

@Entity
public class Product {

    @Id
    private Long id;

    @Column
    private String name;

    @ManyToOne
    private City city;

    public Product() {
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}