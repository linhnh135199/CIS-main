package com.example.demo.app.service.Impl;

import com.example.demo.app.entity.GroupMaster;
import com.example.demo.app.repository.GroupMasterRepository;
import com.example.demo.app.service.GroupMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMasterServiceImpl implements GroupMasterService {

    @Autowired
    private GroupMasterRepository groupMasterRepository;


    @Override
    public List<GroupMaster> getDataByName(String key) {
        return groupMasterRepository.getDataByName(key);
    }

    @Override
    public List<GroupMaster> getAllSpecialized() {
        return (List<GroupMaster>) groupMasterRepository.findAll();
    }
}
