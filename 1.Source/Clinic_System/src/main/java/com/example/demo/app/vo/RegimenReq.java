package com.example.demo.app.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegimenReq {
    Long id;
    String type;
    Long specializedId;
    String name;
    String status;
    Integer page;
    List<Long> ids;
}
