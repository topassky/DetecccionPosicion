package com.example.myapplication.utilities;

public class Utilities {
    public static final String TableUser = "user";
    public static final String Id = "Id";
    public static final String Name = "Name";
    public static final String Latitude = "Latitude";
    public static final String Longitude = "Longitude";
    public static final String Date = "Date";
    public static final String createTable = "CREATE TABLE "+TableUser+" ("+Id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            Name+" TEXT ,"+Latitude+ " REAL ,"+Longitude+ " REAL,"+Date+" TEXT)";
}
