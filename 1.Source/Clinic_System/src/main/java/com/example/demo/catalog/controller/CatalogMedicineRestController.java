package com.example.demo.catalog.controller;


import com.example.demo.app.entity.DiseaseListICD10;
import com.example.demo.app.vo.BookingReq;
import com.example.demo.catalog.request.MedicineReq;
import com.example.demo.catalog.request.MedicineRequest;
import com.example.demo.catalog.response.PageResponse;
import com.example.demo.catalog.service.MedicineService;
import com.example.demo.catalog.vo.Icd10SearchReq;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalogMedicine")
public class CatalogMedicineRestController {
    public static final Logger logger = LoggerFactory.getLogger(CatalogMedicineRestController.class);

    @Autowired
    MedicineService medicineService;

    @GetMapping(value = "/getDataMedicine")
    public ResponseEntity<?> getData(Icd10SearchReq icd10SearchReq) {
        CommonRes commonRes = new CommonRes();
        PageResponse result = medicineService.getData(icd10SearchReq);
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

    @DeleteMapping(value = "/delete/medicine")
    public ResponseEntity<?> deleteMedicine(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        boolean result = medicineService.deleteMedicineById(id);
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

    @PostMapping(value = "/addEditMedicine")
    public ResponseEntity<?> addEditMedicine(MedicineReq medicineReq) {
        CommonRes commonRes = new CommonRes();
        try {
            medicineService.addEditMedicine(medicineReq);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        } catch (Exception e){
            commonRes.setResponseCode(ErrorCode.FAIL.getKey());
            commonRes.setMessage(ErrorCode.FAIL.getValue());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getDataMedicineById")
    public ResponseEntity<?> getDataMedicineById(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            MedicineReq result = medicineService.getDataMedicineById(id);
            commonRes.setData(result);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

}
