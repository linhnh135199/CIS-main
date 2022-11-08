/**
* create date Jul 31, 2019
* create by Mr.Duc
**/

package com.example.demo.masterdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MasterDataController {

  /**
   * Do execute.
   *
   * @return the string
   */
  @RequestMapping("/masterdata-config-management")
  public String doExecute() {
    return "masterdata/masterdata-config-management";
  }
  
}
