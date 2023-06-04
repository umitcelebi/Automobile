package com.ucelebi.automobile.model;

import jakarta.persistence.Entity;

@Entity
public class VehicleFeature extends Item{
    private String brand;
    private String model;
    private String years;
    private String version;
    private String fuelType;
    private String gearType;
    private String cylinderDisplacement;
    private String horsepower;
    private String maximumTorque;
    private String acceleration;
    private String maximumSpeed;
    private String innerCityAvgConsumption;
    private String outOfCityAvgConsumption;
    private String mixedFuelConsumption;
    private String length;
    private String width;
    private String height;
    private String luggageVolume;
    private String curbWeight;
    private String tank;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String year) {
        this.years = year;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    public String getCylinderDisplacement() {
        return cylinderDisplacement;
    }

    public void setCylinderDisplacement(String cylinderDisplacement) {
        this.cylinderDisplacement = cylinderDisplacement;
    }

    public String getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(String horsepower) {
        this.horsepower = horsepower;
    }

    public String getMaximumTorque() {
        return maximumTorque;
    }

    public void setMaximumTorque(String maximumTorque) {
        this.maximumTorque = maximumTorque;
    }

    public String getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(String acceleration) {
        this.acceleration = acceleration;
    }

    public String getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(String maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public String getInnerCityAvgConsumption() {
        return innerCityAvgConsumption;
    }

    public void setInnerCityAvgConsumption(String innerCityAvgConsumption) {
        this.innerCityAvgConsumption = innerCityAvgConsumption;
    }

    public String getOutOfCityAvgConsumption() {
        return outOfCityAvgConsumption;
    }

    public void setOutOfCityAvgConsumption(String outOfCityAvgConsumption) {
        this.outOfCityAvgConsumption = outOfCityAvgConsumption;
    }

    public String getMixedFuelConsumption() {
        return mixedFuelConsumption;
    }

    public void setMixedFuelConsumption(String mixedFuelConsumption) {
        this.mixedFuelConsumption = mixedFuelConsumption;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLuggageVolume() {
        return luggageVolume;
    }

    public void setLuggageVolume(String luggageVolume) {
        this.luggageVolume = luggageVolume;
    }

    public String getCurbWeight() {
        return curbWeight;
    }

    public void setCurbWeight(String curbWeight) {
        this.curbWeight = curbWeight;
    }

    public String getTank() {
        return tank;
    }

    public void setTank(String tank) {
        this.tank = tank;
    }
}
