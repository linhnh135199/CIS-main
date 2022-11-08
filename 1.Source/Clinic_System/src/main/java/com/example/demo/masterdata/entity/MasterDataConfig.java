/**
* create date Aug 2, 2019
* create by Mr.Duc
**/

package com.example.demo.masterdata.entity;

import com.example.demo.common.Constants;

import javax.persistence.*;

@Entity
@Table(name = Constants.TABLE_MASTER_DATA_CONFIG)
public class MasterDataConfig {

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Long id;
  
  /** The name. */
  @Column(name = "CONFIG_NAME", nullable = false)
  private String configName;
  
  /** The table. */
  @Column(name = "CONFIG_TABLE", nullable = false)
  private String configTable;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getConfigName() {
    return configName;
  }

  public void setConfigName(String configName) {
    this.configName = configName;
  }

  public String getConfigTable() {
    return configTable;
  }

  public void setConfigTable(String configTable) {
    this.configTable = configTable;
  }
  
  
}
