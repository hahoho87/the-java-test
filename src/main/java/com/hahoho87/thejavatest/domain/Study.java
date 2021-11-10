package com.hahoho87.thejavatest.domain;

import com.hahoho87.thejavatest.study.StudyStatus;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class Study {

    private int limit;

    private Member owner;
    private String name;

    private LocalDateTime openDateTime;
    private StudyStatus status;

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

    public LocalDateTime getOpenDateTime() {
        return openDateTime;
    }

    public StudyStatus getStatus() {
        return status;
    }

    public void open() {
        this.openDateTime = LocalDateTime.now();
        this.status = StudyStatus.OPENED;
    }
}
