package com.example.demo.app.service.Impl;

import com.example.demo.app.entity.*;
import com.example.demo.app.repository.GenericMasterRepository;
import com.example.demo.app.repository.GroupMasterRepository;
import com.example.demo.app.repository.MedicineRouteRepository;
import com.example.demo.app.repository.RouteMasterRepository;
import com.example.demo.app.service.MedicineService;
import com.example.demo.catalog.repository.MedicineMasterRepository;
import com.example.demo.catalog.request.MedicineReq;
import com.example.demo.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineMasterRepository medicineMasterRepository;
    @Autowired
    private MedicineRouteRepository medicineRouteRepository;
    @Autowired
    private RouteMasterRepository routeMasterRepository;
    @Autowired
    private GenericMasterRepository genericMasterRepository;

    @Override
    public List<MedicineMaster> getDataByName(String name) {
        Pageable pageable = PageRequest.of(0, Constants.PAGE_SIZE);
        Page<MedicineMaster> medicines = medicineMasterRepository.getDataByName(name, pageable);
        return medicines.getContent();
    }

    @Override
    public MedicineReq getDataById(Long id) {
        MedicineReq medicineReq = new MedicineReq();
        Optional<MedicineMaster> medicine = medicineMasterRepository.findById(id);
        if(medicine.isPresent()){
            medicineReq.setId(medicine.get().getId());
            medicineReq.setName(medicine.get().getName());
            medicineReq.setDongGoi(medicine.get().getDongGoi());
            MedicineRoute medicineRoute = medicineRouteRepository.findByMedicineMasterId(medicine.get().getId());
            if(medicineRoute != null){
                Optional<RouteMaster> routeMaster = routeMasterRepository.findById(medicineRoute.getId());
                if (routeMaster.isPresent()){
                    medicineReq.setDuongDung(routeMaster.get().getName());
                }
            }
            medicineReq.setHamLuong(medicine.get().getUnit());
            medicineReq.setHangSX(medicine.get().getHangSX());
            medicineReq.setNuocSX(medicine.get().getNuocSX());
            List<GenericMaster> genericMasters = genericMasterRepository.findByCode(medicine.get().getGenericMasterCode());
            if(genericMasters != null){
                medicineReq.setHoatChat(genericMasters.get(0).getName());
            }
        }
        return medicineReq;
    }
}
