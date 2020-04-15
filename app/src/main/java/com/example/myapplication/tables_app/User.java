package com.example.myapplication.tables_app;

import java.text.SimpleDateFormat;

public class User {
    private Integer id;
    private String Name;
    private Double Latitude;
    private Double Longitude;
    private SimpleDateFormat date;

    public User(Integer id, String name, Double latitude, Double longitude, SimpleDateFormat date) {
        this.id = id;
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }
}
