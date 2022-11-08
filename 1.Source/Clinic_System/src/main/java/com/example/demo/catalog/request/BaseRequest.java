package com.example.demo.catalog.request;

import lombok.Data;

@Data
public class BaseRequest {
    protected Integer pageIndex;
    protected Integer pageSize;
}
