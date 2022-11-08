package com.example.demo.app.service.Impl;

import com.example.demo.app.entity.*;
import com.example.demo.app.repository.GroupMasterRepository;
import com.example.demo.app.repository.RegimenServiceRepository;
import com.example.demo.app.repository.ServiceOfPatientRepository;
import com.example.demo.app.repository.ServicePriceHistoryRepository;
import com.example.demo.app.service.ServiceService;
import com.example.demo.app.vo.PatientServiceReq;
import com.example.demo.catalog.repository.ServiceMasterRepository;
import com.example.demo.catalog.request.ServiceRes;
import com.example.demo.common.Constants;
import com.example.demo.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceOfPatientRepository serviceOfPatientRepository;

    @Autowired
    private ServiceMasterRepository serviceMasterRepository;


    @Autowired
    private RegimenServiceRepository regimenServiceRepository;

    @Autowired
    private GroupMasterRepository groupMasterRepository;

    @Autowired
    private ServicePriceHistoryRepository servicePriceHistoryRepository;

    @Override
    public List<ServiceRes> getServiceByPatientId(Long id) {
        List<ServiceRes> services = new ArrayList<>();
        List<ServiceOfPatient> serviceOfPatients = serviceOfPatientRepository.findAllByPatientId(id);
        Map<Long, ServiceRes> map = new HashMap<>();
        if(serviceOfPatients != null){
            for (ServiceOfPatient serviceOfPatient: serviceOfPatients){
                if(map.get(serviceOfPatient.getId()) == null){
                    Optional<ServiceMaster> service = serviceMasterRepository.findById(serviceOfPatient.getServiceId());
                    if(service.isPresent()){
                        ServiceRes serviceRes = new ServiceRes();
                        serviceRes.setId(service.get().getId());
                        serviceRes.setName(service.get().getServiceName());
                        serviceRes.setSoLuong(1L);
                        List<ServicePriceHistory> servicePriceHistories = servicePriceHistoryRepository.findByServiceID(service.get().getId());
                        for (ServicePriceHistory servicePriceHistory: servicePriceHistories){
                            if(servicePriceHistory.getToDate() != null &&
                                    (new Date()).before(servicePriceHistory.getToDate()) &&
                                    servicePriceHistory.getFromDate() != null &&
                                    (new Date()).after(servicePriceHistory.getFromDate())){
                                serviceRes.setPrice(servicePriceHistory.getPrice().replaceAll(",", ""));
                            }
                            if(serviceRes.getPrice() == null){
                                serviceRes.setPrice(servicePriceHistory.getPrice().replaceAll(",", ""));
                            }
                        }

                        Optional<GroupMaster> groupMaster = groupMasterRepository.findById(service.get().getGroupId());
                        if(groupMaster.isPresent()){
                            serviceRes.setSpecialized(groupMaster.get().getGroupName());
                            serviceRes.setSpecializedId(groupMaster.get().getId());
                        }
                        map.put(serviceOfPatient.getId(), serviceRes);
                    }
                }else {
                    ServiceRes serviceRes = map.get(serviceOfPatient.getId());
                    serviceRes.setSoLuong(serviceRes.getSoLuong() +1);
                    map.put(serviceOfPatient.getId(), serviceRes);
                }
                services = new ArrayList<ServiceRes>(map.values());
            }
        }
        return services;
    }

    @Override
    public List<ServiceRes> getServiceBySpecializedId(Long id) {
        List<ServiceRes> services = new ArrayList<>();
        List<ServiceMaster> serviceSpecializeds = serviceMasterRepository.findByGroupId(id);
        if(serviceSpecializeds != null){
            for (ServiceMaster service: serviceSpecializeds){
                ServiceRes serviceRes = new ServiceRes();
                serviceRes.setId(service.getId());
                serviceRes.setName(service.getServiceName());
                Optional<GroupMaster> groupMaster = groupMasterRepository.findById(service.getGroupId());
                if(groupMaster.isPresent()){
                    serviceRes.setSpecialized(groupMaster.get().getGroupName());
                    serviceRes.setSpecializedId(groupMaster.get().getId());
                }
                services.add(serviceRes);
            }
        }
        return services;
    }

    @Override
    public List<ServiceRes> getServiceByCriticalPathId(String ids) {
        List<ServiceRes> services = new ArrayList<>();
        if(StringUtils.isNotEmpty(ids)){
            String[] idList = ids.split(",");
            for (String id:idList){
                List<RegimenService> regimenServices = regimenServiceRepository.findAllByRegimenId(Long.parseLong(id));
                if(regimenServices != null){
                    for (RegimenService regimenService: regimenServices){
                        Optional<ServiceMaster> service = serviceMasterRepository.findById(regimenService.getServiceId());
                        if(service.isPresent()){
                            ServiceRes serviceRes = new ServiceRes();
                            serviceRes.setId(service.get().getId());
                            serviceRes.setName(service.get().getServiceName());
                            Optional<GroupMaster> groupMaster = groupMasterRepository.findById(service.get().getGroupId());
                            if(groupMaster.isPresent()){
                                serviceRes.setSpecialized(groupMaster.get().getGroupName());
                                serviceRes.setSpecializedId(groupMaster.get().getId());
                            }
                            services.add(serviceRes);
                        }
                    }
                }
            }
        }
        return services;
    }

    @Override
    public void updateServiceToPatient(PatientServiceReq patientServiceReq) {
        List<ServiceOfPatient> serviceOfPatients = serviceOfPatientRepository.findAllByPatientId(patientServiceReq.getIdPatient());
        for(ServiceOfPatient serviceOfPatient: serviceOfPatients){
            serviceOfPatientRepository.delete(serviceOfPatient);
        }
        if(StringUtils.isNotEmpty(patientServiceReq.getIdServices())){
            String[] idList = patientServiceReq.getIdServices().split(",");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            for (String id:idList){
                Optional<ServiceMaster> service = serviceMasterRepository.findById(Long.parseLong(id));
                if(service.isPresent()){
                    ServiceOfPatient serviceOfPatient = new ServiceOfPatient();
                    serviceOfPatient.setPatientId(patientServiceReq.getIdPatient());
                    serviceOfPatient.setServiceId(service.get().getId());
                    serviceOfPatient.setCreatedTime(new Date());
                    serviceOfPatient.setCreatedUser(user.getName());
                    serviceOfPatientRepository.save(serviceOfPatient);
                }
            }
        }
    }

    @Override
    public List<ServiceMaster> getDataByNameAndSpecialized(String key, Long specializedId) {
        Pageable pageable = PageRequest.of(0, Constants.PAGE_SIZE);
        Page<ServiceMaster> medicines = serviceMasterRepository.getDataByNameAndSpecialized(key, specializedId, pageable);
        return medicines.getContent();
    }
}
