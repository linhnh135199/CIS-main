/**
* create date Aug 2, 2019
* create by Mr.Duc
**/

package com.example.demo.masterdata.repository;

import com.example.demo.masterdata.entity.MasterDataConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterDataConfigRepository extends CrudRepository<MasterDataConfig, Long> {

}
