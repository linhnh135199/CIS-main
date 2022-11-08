package com.example.demo.masterdata.service.impl;

import com.example.demo.masterdata.entity.MasterData;
import com.example.demo.masterdata.repository.MasterDataRepository;
import com.example.demo.masterdata.service.MasterDataService;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MasterDataServiceImpl implements MasterDataService {

	/** The master data repository. */
	@Autowired
	MasterDataRepository masterDataRepository;
	
	@Autowired
	UserService userService;
	

	@Override
	@Cacheable(value="master_data", key="{#name, #category}")
	public MasterData getMasterDataByName(String name, String category) {
		return masterDataRepository.findByNameAndCategory(name, category);
	}

	@Override
	public void initData() {
		MasterData initDefault = masterDataRepository.findByNameAndCategory("INITED_DEFAULT_DATA", null);
		
		// init data if config init default data is true
		if(initDefault == null || !Boolean.valueOf(initDefault.getValue())) {

			// init user admin data
			userService.initAdmin();

			if(initDefault != null) {
				initDefault.setValue("true");
			} else {
				initDefault = new MasterData();
				initDefault.setCategory("");
				initDefault.setDescription("Check inited data default");
				initDefault.setValue("true");
				initDefault.setName("INITED_DEFAULT_DATA");
			}
			masterDataRepository.save(initDefault);
		}
		
		
	}


	@Override
	  @CacheEvict(value="master_data", key="{#name, #category}")
	  public void delete(long id) {
    masterDataRepository.deleteById(id);
  }

}
