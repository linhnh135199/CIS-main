package com.example.demo.user.vo;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
public class UserRequet {

    private Long id;

    private String fullName;

    private String password;

    private String email;

    private String phone;

    private String address;

    private String gender;

    private String imageUrl;

    private String birthOfDate;

    private String role;

}