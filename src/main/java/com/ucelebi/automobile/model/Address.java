package com.ucelebi.automobile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Address extends Item{
    @Column(nullable = false, unique = true)
    private String code;
    private String postalCode;
    @ManyToOne
    private Country country;
    @ManyToOne
    private City city;
    @ManyToOne
    private Town town;
    private String streetName;
    private String streetNumber;
    private String line;
    @OneToOne(mappedBy = "address")
    private Partner partner;

    public Address() {}

    public Address(String code, String postalCode, Country country, City city, Town town, String streetName, String streetNumber, String line) {
        this.code = code;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
        this.town = town;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.line = line;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
