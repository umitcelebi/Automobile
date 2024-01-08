package com.ucelebi.automobile.dto;

public class WorkingTimesDTO {
    private String day;
    private String openingTime;
    private String closingTime;

    public WorkingTimesDTO() {}

    public WorkingTimesDTO(String day, String openingTime, String closingTime) {
        this.day = day;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }
}
