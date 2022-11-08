package com.example.demo.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BasicEntity implements Serializable {

    @Column(name = "CREATED_TIME")
    private Date createdTime;

    @Column(name = "MODIFIED_TIME")
    private Date modifiedTime;

    @Column(name = "CREATED_USER")
    private String createdUser;

    @Column(name = "MODIFIED_USER")
    private String modifiedUser;
}
