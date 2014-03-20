package com.mistergo.myworkouts;

public class MyPeople {
    private int id;
    private String name;
    private boolean isActual;

    public MyPeople(int id, String name, boolean actual) {
        this.id = id;
        this.isActual = actual;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getActual() {
        return isActual;
    }

    public void setActual(boolean actual) {
        isActual = actual;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
