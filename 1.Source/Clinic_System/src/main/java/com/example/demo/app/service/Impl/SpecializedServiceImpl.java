//package com.example.demo.app.service.Impl;
//
//import com.example.demo.app.entity.Specialized;
//import com.example.demo.app.repository.SpecializedRepository;
//import com.example.demo.app.service.SpecializedService;
//import com.example.demo.common.Constants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class SpecializedServiceImpl implements SpecializedService {
//
//    @Autowired
//    private SpecializedRepository specializedRepository;
//
//    @Override
//    public List<Specialized> getDataByName(String name) {
//        Pageable pageable = PageRequest.of(0, Constants.PAGE_SIZE);
//        Page<Specialized> medicines = specializedRepository.getDataByName(name, pageable);
//        return medicines.getContent();
//    }
//
//    @Override
//    public List<Specialized> getAllSpecialized() {
//        List<Specialized> specializedList = (List<Specialized>) specializedRepository.findAll();
//        return specializedList;
//    }
//}
