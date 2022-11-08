package com.example.demo;

import com.example.demo.masterdata.service.MasterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

	/** The master data service. */
	@Autowired
	MasterDataService masterDataService;

	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		masterDataService.initData();
	}

}
