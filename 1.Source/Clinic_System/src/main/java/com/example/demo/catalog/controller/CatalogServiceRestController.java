package com.example.demo.catalog.controller;

import com.example.demo.catalog.request.ServiceRequest;
import com.example.demo.catalog.request.ServiceRes;
import com.example.demo.catalog.response.PageResponse;
import com.example.demo.catalog.service.ServiceService;
import com.example.demo.catalog.vo.ServiceSearchReq;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogService")
public class CatalogServiceRestController {
    public static final Logger logger = LoggerFactory.getLogger(CatalogServiceRestController.class);
    @Autowired
    ServiceService serviceService;

    @GetMapping(value = "/getDataService")
    public ResponseEntity<?> getData(ServiceSearchReq serviceSearchReq) {
        CommonRes commonRes = new CommonRes();
        PageResponse result = serviceService.getData(serviceSearchReq);
        if (result != null) {
            commonRes.setResponseCode(ErrorCode.SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.SUCCESS.getValue());
            commonRes.setData(result);
        }else{
            commonRes.setResponseCode(ErrorCode.DATA_NOT_FOUND.getKey());
            commonRes.setMessage(ErrorCode.DATA_NOT_FOUND.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @DeleteMapping(value = "/delete/service")
    public ResponseEntity<?> deleteMedicine(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        boolean result = serviceService.deleteServiceById(id);
        if (result) {
            commonRes.setResponseCode(ErrorCode.SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.SUCCESS.getValue());
            commonRes.setData(result);
        }else{
            commonRes.setResponseCode(ErrorCode.FAIL.getKey());
            commonRes.setMessage(ErrorCode.FAIL.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/addEditService")
    public ResponseEntity<?> addEditService(ServiceRes serviceRes) {

        CommonRes commonRes = new CommonRes();
        try {
            serviceService.addEditService(serviceRes);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.FAIL.getKey());
            commonRes.setMessage(ErrorCode.FAIL.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }
}
