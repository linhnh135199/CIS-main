package com.example.demo.user.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserSearchReq {
    private String role;

    private Integer page;

    private String key;
}