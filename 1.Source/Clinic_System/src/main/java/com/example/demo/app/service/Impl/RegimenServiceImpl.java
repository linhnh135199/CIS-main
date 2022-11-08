package com.example.demo.app.service.Impl;

import com.example.demo.app.entity.*;
import com.example.demo.app.repository.*;
import com.example.demo.app.service.CMSRegimenService;
import com.example.demo.app.vo.MedicineDto;
import com.example.demo.app.vo.RegimenDto;
import com.example.demo.app.vo.RegimenReq;
import com.example.demo.app.vo.RegimenRes;
import com.example.demo.catalog.repository.MedicineMasterRepository;
import com.example.demo.catalog.repository.ServiceMasterRepository;
import com.example.demo.catalog.request.MedicineReq;
import com.example.demo.catalog.request.ServiceRes;
import com.example.demo.common.Constants;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RegimenServiceImpl implements CMSRegimenService {

    @Autowired
    private RegimenRepository regimenRepository;

    @Autowired
    private RegimenSpecializedRepository regimenSpecializedRepository;

    @Autowired
    private ServiceMasterRepository serviceMasterRepository;

    @Autowired
    private RegimenServiceRepository regimenServiceRepository;

    @Autowired
    private RegimenMedicineRepository regimenMedicineRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupMasterRepository groupMasterRepository;

    @Autowired
    private MedicineMasterRepository medicineMasterRepository;

    @Autowired
    private MedicineRouteRepository medicineRouteRepository;
    @Autowired
    private RouteMasterRepository routeMasterRepository;
    @Autowired
    private GenericMasterRepository genericMasterRepository;


    @Override
    public List<Regimen> getCriticalPathBySpecializedId(Long id) {
        List<Regimen> regimens = new ArrayList<>();
        List<RegimenSpecialized> regimenSpecializeds = regimenSpecializedRepository.findAllBySpecializedId(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if(regimenSpecializeds != null){
            for (RegimenSpecialized regimenSpecialized: regimenSpecializeds){
                Optional<Regimen> regimen = regimenRepository.findById(regimenSpecialized.getRegimenId());
                if(regimen.isPresent() && (regimen.get().getStatus().equalsIgnoreCase(Constants.PUBLIC)) ||
                        (regimen.get().getStatus().equalsIgnoreCase(Constants.PRIVATE)) && regimen.get().getCreateUserId() == user.getId()
                    && regimen.get().getType().equalsIgnoreCase(Constants.SERVICE)){
                    regimens.add(regimen.get());
                }
            }
        }
        return regimens;
    }

    @Override
    public RegimenRes getRegimen(RegimenReq regimenReq) {
        RegimenRes regimenRes = new RegimenRes();
        int page = 1;
        if(regimenReq.getPage() != null){
            page = regimenReq.getPage();
        }
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, Constants.PAGE_SIZE);
        Page<Regimen> regimens = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if(regimenReq.getSpecializedId() == null){
            regimens = regimenRepository.getAllDataByDataSearchAllSpecialized(regimenReq.getType(),
                    regimenReq.getName(), regimenReq.getStatus(), user.getId(), pageable);
        }else{
            regimens = regimenRepository.getAllDataByDataSearch(regimenReq.getType(),
                    regimenReq.getSpecializedId(), regimenReq.getName(), regimenReq.getStatus(), user.getId(), pageable);
        }

        if(regimens != null){
            regimenRes.setTotal(regimens.getTotalElements());
            List<Regimen> regimenList = regimens.getContent();
            List<RegimenDto> regimenDtos = new ArrayList<>();
            for (Regimen regimen:regimenList){
                RegimenDto regimenDto = new RegimenDto();
                regimenDto.setId(regimen.getId());
                regimenDto.setName(regimen.getName());
                regimenDto.setStatus(regimen.getStatus());
                regimenDto.setType(regimen.getType());
                User user1 = userService.findById(regimen.getCreateUserId());
                regimenDto.setCreateUser(user1.getName());
                List<RegimenSpecialized> regimenSpecializeds = regimenSpecializedRepository.findAllByRegimenId(regimen.getId());
                if(regimenSpecializeds != null){
                    Optional<GroupMaster> specialized = groupMasterRepository.findById(regimenSpecializeds.get(0).getSpecializedId());
                    if(specialized.isPresent()){
                        regimenDto.setSpecializedId(specialized.get().getId());
                        regimenDto.setSpecialized(specialized.get().getGroupName());
                    }
                }
                regimenDtos.add(regimenDto);
            }
            regimenRes.setRegimens(regimenDtos);
        }
        return regimenRes;
    }

    @Override
    public void addEditRegimen(RegimenReq regimenReq) {
        Regimen regimen = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if(regimenReq.getId() != null){
            Optional<Regimen> regimenOld = regimenRepository.findById(regimenReq.getId());
            if(regimenOld.isPresent()){
                regimen = regimenOld.get();
            }
            regimen.setModifiedTime(new Date());
            regimen.setModifiedUser(user.getName());
            regimen.setCreateUserId(user.getId());
        }else{
            regimen = new Regimen();
            regimen.setCreatedTime(new Date());
            regimen.setCreatedUser(user.getName());
        }
        regimen.setName(regimenReq.getName());
        regimen.setStatus(regimenReq.getStatus());
        if(regimenReq.getType().equalsIgnoreCase("service")){
            regimen.setType("Dịch vụ kỹ thuật");
        }else{
            regimen.setType("Thuốc");
        }
        regimen.setCreateUserId(user.getId());
        regimen = regimenRepository.save(regimen);
        if(regimenReq.getIds() != null){
            if(regimenReq.getType().equalsIgnoreCase("service")){
                List<RegimenService> regimenServices = regimenServiceRepository.findAllByRegimenId(regimen.getId());
                for (RegimenService regimenService: regimenServices){
                    regimenServiceRepository.delete(regimenService);
                }
                for (Long id: regimenReq.getIds()){
                    Optional<ServiceMaster> service = serviceMasterRepository.findById(id);
                    if(service.isPresent()){
                        RegimenService regimenService = new RegimenService();
                        regimenService.setRegimenId(regimen.getId());
                        regimenService.setServiceId(service.get().getId());
                        regimenService.setCreatedUser(user.getName());
                        regimenService.setCreatedTime(new Date());
                        regimenServiceRepository.save(regimenService);
                    }
                }
            }else if(regimenReq.getType().equalsIgnoreCase("medicine")){
                List<RegimenMedicine> regimenMedicines = regimenMedicineRepository.findAllByRegimenId(regimen.getId());
                for (RegimenMedicine regimenMedicine: regimenMedicines){
                    regimenMedicineRepository.delete(regimenMedicine);
                }
                for (Long id: regimenReq.getIds()){
                    Optional<MedicineMaster> medicine = medicineMasterRepository.findById(id);
                    if(medicine.isPresent()){
                        RegimenMedicine regimenMedicine = new RegimenMedicine();
                        regimenMedicine.setRegimenId(regimen.getId());
                        regimenMedicine.setMedicineId(medicine.get().getId());
                        regimenMedicine.setCreatedUser(user.getName());
                        regimenMedicine.setCreatedTime(new Date());
                        regimenMedicineRepository.save(regimenMedicine);
                    }
                }
            }

        }
        RegimenSpecialized regimenSpecialized =
                regimenSpecializedRepository.findAllByRegimenIdAndSpecializedId(regimen.getId(), regimenReq.getSpecializedId());
        if(regimenSpecialized == null){
            regimenSpecialized = new RegimenSpecialized();
            regimenSpecialized.setCreatedTime(new Date());
            regimenSpecialized.setCreatedUser(user.getName());
            regimenSpecialized.setRegimenId(regimen.getId());
            regimenSpecialized.setSpecializedId(regimenReq.getSpecializedId());
            regimenSpecializedRepository.save(regimenSpecialized);
        }
    }

    @Override
    public RegimenDto getRegimenById(Long id) {
        RegimenDto regimenDto = new RegimenDto();
        Optional<Regimen> regimenOld = regimenRepository.findById(id);
        if(regimenOld.isPresent()){
            regimenDto.setId(regimenOld.get().getId());
            regimenDto.setName(regimenOld.get().getName());
            regimenDto.setType(regimenOld.get().getType());
            regimenDto.setStatus(regimenOld.get().getStatus());
            List<ServiceRes> services = new ArrayList<>();
            List<RegimenService> regimenServices = regimenServiceRepository.findAllByRegimenId(id);
            for (RegimenService regimenService : regimenServices){
                Optional<ServiceMaster> service = serviceMasterRepository.findById(regimenService.getServiceId());
                if(service.isPresent()){
                    ServiceRes serviceRes = new ServiceRes();
                    serviceRes.setId(service.get().getId());
                    serviceRes.setName(service.get().getServiceName());
                    Optional<GroupMaster> groupMasters = groupMasterRepository.findById(service.get().getGroupId());
                    if(groupMasters.isPresent()){
                        serviceRes.setSpecialized(groupMasters.get().getGroupName());
                        serviceRes.setSpecializedId(groupMasters.get().getId());
                    }
                    services.add(serviceRes);
                }
            }
            List<RegimenSpecialized> regimenSpecializeds = regimenSpecializedRepository.findAllByRegimenId(id);
            if(regimenSpecializeds != null){
                Optional<GroupMaster> specialized = groupMasterRepository.findById(regimenSpecializeds.get(0).getSpecializedId());
                if(specialized.isPresent()){
                    regimenDto.setSpecializedId(specialized.get().getId());
                    regimenDto.setSpecialized(specialized.get().getGroupName());
                }
            }
            regimenDto.setServices(services);
        }
        return regimenDto;
    }

    @Override
    public MedicineDto getRegimenMedicineById(Long id) {
        MedicineDto medicineDto = new MedicineDto();
        Optional<Regimen> regimenOld = regimenRepository.findById(id);
        if(regimenOld.isPresent()){
            medicineDto.setId(regimenOld.get().getId());
            medicineDto.setName(regimenOld.get().getName());
            medicineDto.setType(regimenOld.get().getType());
            medicineDto.setStatus(regimenOld.get().getStatus());
            List<MedicineReq> medicines = new ArrayList<>();
            List<RegimenMedicine> regimenMedicines = regimenMedicineRepository.findAllByRegimenId(id);
            for (RegimenMedicine regimenMedicine : regimenMedicines){
                Optional<MedicineMaster> medicine = medicineMasterRepository.findById(regimenMedicine.getMedicineId());
                if(medicine.isPresent()){
                    MedicineReq medicineReq = new MedicineReq();
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
                    medicines.add(medicineReq);
                }
            }
            List<RegimenSpecialized> regimenSpecializeds = regimenSpecializedRepository.findAllByRegimenId(id);
            if(regimenSpecializeds != null){
                Optional<GroupMaster> specialized = groupMasterRepository.findById(regimenSpecializeds.get(0).getSpecializedId());
                if(specialized.isPresent()){
                    medicineDto.setSpecializedId(specialized.get().getId());
                    medicineDto.setSpecialized(specialized.get().getGroupName());
                }
            }
            medicineDto.setMedicines(medicines);
        }
        return medicineDto;
    }

    @Override
    public List<Regimen> getRegimenAllPrescription() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        List<Regimen> regimens = regimenRepository.getRegimenAllPrescription(user.getId());
        return regimens;
    }
}
