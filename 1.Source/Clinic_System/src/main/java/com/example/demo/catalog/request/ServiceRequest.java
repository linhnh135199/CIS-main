package com.example.demo.catalog.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRequest extends BaseRequest{
    private String serviceName;
    private String groupName;
}
