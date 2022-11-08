/**
* create date Aug 2, 2019
* create by Mr.Duc
**/

package com.example.demo.masterdata.service.impl;

import com.example.demo.masterdata.entity.MasterDataConfig;
import com.example.demo.masterdata.repository.MasterDataConfigRepository;
import com.example.demo.masterdata.service.MasterDataConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterDataConfigServiceImpl implements MasterDataConfigService {

  @Autowired
  MasterDataConfigRepository masterDataConfigRepository;
  
  
  @Override
  public List<MasterDataConfig> finAll() {
    return (List<MasterDataConfig>) masterDataConfigRepository.findAll();
  }
}
