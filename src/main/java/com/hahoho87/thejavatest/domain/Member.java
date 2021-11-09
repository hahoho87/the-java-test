package com.hahoho87.thejavatest.domain;

import lombok.Data;

@Data
public class Member {

    private Long memberId;
    private String name;

    public Member(Long memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public Member() {
    }
}
