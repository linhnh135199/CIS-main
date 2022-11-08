package com.example.demo.masterdata.controller;

import com.example.demo.masterdata.entity.MasterData;
import com.example.demo.masterdata.service.MasterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/masterdata")
public class MasterDataRestController {

	/** The master data service. */
	@Autowired
    MasterDataService masterDataService;

	/**
	 * Gets the option config.
	 *
	 * @return the option config
	 */
	@RequestMapping(value = "/getOptionConfig", method = RequestMethod.GET)
	public ResponseEntity<String> getOptionConfig() {
		String value = "";
		MasterData masterData = masterDataService.getMasterDataByName("OPTION_CONFIG", "CHART");
		if (masterData != null) {
			value = masterData.getValue();
		}
		return new ResponseEntity<String>(value, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAdminOptionConfig", method = RequestMethod.GET)
	public ResponseEntity<String> getAdminOptionConfig() {
		String value = "";
		MasterData masterData = masterDataService.getMasterDataByName("ADMIN_CHART_OPTION_CONFIG", "CHART");
		if (masterData != null) {
			value = masterData.getValue();
		}
		return new ResponseEntity<String>(value, HttpStatus.OK);
	}

  
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE })
  @ResponseBody
  public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
    try {
      masterDataService.delete(id);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<Object>(HttpStatus.OK);
  }
  
  
}
