package com.example.demo.catalog.dto;

import com.example.demo.app.entity.GroupMaster;
import com.example.demo.app.entity.ServiceMaster;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ServiceDto {
    private Long id;
    private Long groupId;
    private String groupName;
    private String serviceName;
    private String code;

    public ServiceDto(ServiceMaster s, GroupMaster g) {
        this.id = s.getId();
        this.groupId = g.getId();
        this.groupName = g.getGroupName();
        this.serviceName = s.getServiceName();
        this.code = s.getCode();
    }
}
