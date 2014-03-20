package com.mistergo.myworkouts;

public class MyMonth {
    public int id;
    public int year;
    public int month;
    public float cost;

    public MyMonth(int id, int year, int month, float cost) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
