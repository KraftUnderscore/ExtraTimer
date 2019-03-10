package com.pieta.zapis;

public class Data {
    private String name;
    private long time;
    private int day, month, year;

    public Data(String name, long time, int day, int month, int year){
        this.name = name;
        this.time = time;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }


    public int getYear() {
        return year;
    }
}
