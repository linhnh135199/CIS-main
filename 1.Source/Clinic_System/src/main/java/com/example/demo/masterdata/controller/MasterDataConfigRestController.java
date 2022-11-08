/**
* create date Aug 2, 2019
* create by Mr.Duc
**/

package com.example.demo.masterdata.controller;

import com.example.demo.masterdata.entity.MasterDataConfig;
import com.example.demo.masterdata.service.MasterDataConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/masterdataConfig")
public class MasterDataConfigRestController {
  
  @Autowired
  MasterDataConfigService masterDataConfigService;

  @RequestMapping(value = "/findAll", method = RequestMethod.GET)
  public ResponseEntity<Object> finAll() {
    List<MasterDataConfig> listConfig = masterDataConfigService.finAll();
    return new ResponseEntity<Object>(listConfig, HttpStatus.OK);
  }
}
