package com.example.demo.masterdata.service;

import com.example.demo.masterdata.entity.MasterData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface MasterDataService {

	/**
	 * Gets the master data by name.
	 *
	 * @param name     the name
	 * @param category the category
	 * @return the master data by name
	 */
	MasterData getMasterDataByName(String name, String category);

	/**
	 * Inits the master data.
	 */
	void initData();

  
    void delete(long id);
	
	
}
