package com.example.demo.user.vo;

import com.example.demo.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class UserSearchRes {
    private Long total;
    private List<User> listUser;
}