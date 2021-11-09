package com.hahoho87.thejavatest.domain;

import lombok.ToString;

@ToString
public class Study {

    private int limit;

    private Member owner;
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


    public Study() {
    }

    public Study(int limit) {
        this.limit = limit;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }
}
