package com.example.demo.catalog.service;

import com.example.demo.catalog.dto.ServiceDto;
import com.example.demo.catalog.request.ServiceRequest;
import com.example.demo.catalog.request.ServiceRes;
import com.example.demo.catalog.response.PageResponse;
import com.example.demo.catalog.vo.ServiceSearchReq;

import java.util.List;

public interface ServiceService {
    List<ServiceDto> getAll();

    PageResponse getData(ServiceSearchReq serviceSearchReq);

    PageResponse getDataByPage(ServiceSearchReq serviceSearchReq);

    boolean deleteServiceById(long id);

    void addEditService(ServiceRes serviceRes);
}
