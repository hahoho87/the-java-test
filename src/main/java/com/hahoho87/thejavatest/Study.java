package com.hahoho87.thejavatest;

public class Study {

    private int limit;


    private String name;

    public int getLimit() {
        return limit;
    }
    public String getName() {
        return name;
    }

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }

    public Study() {
    }

    public Study(int limit) {
        this.limit = limit;
    }
}
