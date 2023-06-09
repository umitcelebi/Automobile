package com.ucelebi.automobile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Town extends Item{
    @Column(nullable = false,unique = true)
    private String code;
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    private City city;
    @JsonIgnore
    @OneToMany(mappedBy = "town")
    private List<Address> addresses;

    public Town() {}

    public Town(String code, String name, City city) {
        this.code = code;
        this.name = name;
        this.city = city;
    }

    public Town(Long id, Timestamp creationTime, Timestamp modifiedTime, boolean active, String code, String name, City city, List<Address> addresses) {
        super(id, creationTime, modifiedTime, active);
        this.code = code;
        this.name = name;
        this.city = city;
        this.addresses = addresses;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
