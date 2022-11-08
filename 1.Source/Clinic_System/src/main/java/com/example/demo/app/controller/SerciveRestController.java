package com.example.demo.app.controller;

import com.example.demo.app.entity.Regimen;
import com.example.demo.app.entity.ServiceMaster;
import com.example.demo.app.service.CMSRegimenService;
import com.example.demo.app.service.ServiceService;
import com.example.demo.app.vo.PatientServiceReq;
import com.example.demo.catalog.request.ServiceRes;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/api/service")
public class SerciveRestController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private CMSRegimenService regimenService;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getServiceById", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getServiceById(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            List<ServiceRes> services = serviceService.getServiceByPatientId(id);
            commonRes.setData(services);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    /**
     *
     * Using it to find Service by Specialized id.
     * @param id
     * @return
     */

    @GetMapping(value = "/getServiceByChuyenNganhId", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getServiceBySpecializedId(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            List<ServiceRes> services = serviceService.getServiceBySpecializedId(id);
            commonRes.setData(services);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    /**
     * Using it to find Critical Path by Specialized  id.
     * @param id
     * @return
     */
    @GetMapping(value = "/getPhacDoByChuyenNganhId", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getCriticalPathBySpecializedId(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            List<Regimen> regimens = regimenService.getCriticalPathBySpecializedId(id);
            commonRes.setData(regimens);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getServiceByPhacDoIds", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getServiceByCriticalPathId(@RequestParam("ids") String ids) {
        CommonRes commonRes = new CommonRes();
        try {
            List<ServiceRes> services = serviceService.getServiceByCriticalPathId(ids);
            commonRes.setData(services);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/updateServiceToPatient", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> updateServiceToPatient(PatientServiceReq patientServiceReq) {
        CommonRes commonRes = new CommonRes();
        try {
            serviceService.updateServiceToPatient(patientServiceReq);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getDataSearch", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getDataSearch(@RequestParam("key") String key, @RequestParam("specializedId") Long specializedId) {
        CommonRes commonRes = new CommonRes();
        try {
            if (StringUtils.isNotEmpty(key)){
                key = key.trim();
            }
            List<ServiceMaster> services = serviceService.getDataByNameAndSpecialized(key, specializedId);
            commonRes.setData(services);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }
}
