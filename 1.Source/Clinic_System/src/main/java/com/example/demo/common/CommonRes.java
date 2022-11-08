package com.example.demo.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonRes {
    private String responseCode;
    private String message;
    private Object data;
}
