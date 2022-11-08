package com.example.demo.app.service;

import com.example.demo.app.entity.GroupMaster;

import java.util.List;

public interface GroupMasterService {

    List<GroupMaster> getDataByName(String key);

    List<GroupMaster> getAllSpecialized();
}
