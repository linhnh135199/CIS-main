package com.example.demo.catalog.service.implement;

import com.example.demo.app.entity.GenericMaster;
import com.example.demo.app.entity.MedicineMaster;
import com.example.demo.app.entity.MedicineRoute;
import com.example.demo.app.entity.RouteMaster;
import com.example.demo.app.repository.GenericMasterRepository;
import com.example.demo.app.repository.MedicineRouteRepository;
import com.example.demo.app.repository.RouteMasterRepository;
import com.example.demo.catalog.dto.MedicineDto;
import com.example.demo.catalog.repository.MedicineMasterRepository;
import com.example.demo.catalog.request.MedicineReq;
import com.example.demo.catalog.response.PageResponse;
import com.example.demo.catalog.service.MedicineService;
import com.example.demo.catalog.vo.Icd10SearchReq;
import com.example.demo.common.Constants;
import com.example.demo.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImplement extends AbstractService implements MedicineService {

    @Autowired
    private MedicineMasterRepository medicineMasterRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RouteMasterRepository routeMasterRepository;
    @Autowired
    private MedicineRouteRepository medicineRouteRepository;
    @Autowired
    private GenericMasterRepository genericMasterRepository;

    @SuppressWarnings("unchecked")
    @Override
    public List<MedicineDto> getAll() {
        try {
            StringBuilder strQ = new StringBuilder(
                    "select new com.example.demo.catalog.dto.MedicineDto(m, g, r) from MedicineMaster m " +
                            "inner join MedicineRoute mr on m.id = mr.medicineMasterId " +
                            "inner join RouteMaster r on mr.routeMasterId = r.id " +
                            "inner join GenericMaster g on m.genericMasterCode = g.code");
            Query query = entityManager.createQuery(strQ.toString(), MedicineDto.class);
            List<MedicineDto> result = query.getResultList();
            if(result != null)
                return result;
        } catch (Exception e){
            logger.error("get all with error : {} | {}", e.getMessage(), e.getMessage().toString());
        }
        return null;

    }

    @Override
    public boolean deleteMedicineById(long id) {
        try {
            if(id > 0) {
                Optional<MedicineMaster> medicineOptional = medicineMasterRepository.findById(id);
                if(medicineOptional.isPresent()){
                    medicineMasterRepository.deleteById(id);
                    return true;
                }
            }
        }catch (Exception e){

        }
        return false;
    }


    @Override
    @SuppressWarnings("unchecked")
    public PageResponse getData(Icd10SearchReq icd10SearchReq) {
        try {
            PageResponse response = new PageResponse();
            Pageable paging = PageRequest.of(icd10SearchReq.getPage() - 1, Constants.PAGE_SIZE);
            StringBuilder strQ = new StringBuilder(
                    "select new com.example.demo.catalog.dto.MedicineDto(m, g, r) from MedicineMaster m " +
                            "inner join MedicineRoute mr on m.id = mr.medicineMasterId " +
                            "inner join RouteMaster r on mr.routeMasterId = r.id " +
                            "inner join GenericMaster g on m.genericMasterCode = g.code ");
            StringBuilder strC = new StringBuilder(
                    "select count(distinct m.id) from MedicineMaster m " +
                            "inner join MedicineRoute mr on m.id = mr.medicineMasterId " +
                            "inner join RouteMaster r on mr.routeMasterId = r.id " +
                            "inner join GenericMaster g on m.genericMasterCode = g.code ");
            if(StringUtils.isNotEmpty(icd10SearchReq.getKey())){
                strQ.append(" where (m.name like :name");
                strQ.append(" or m.unit like :unit)");
                strC.append(" where (m.name like :name");
                strC.append(" or m.unit like :unit)");
            }
            Query query = entityManager.createQuery(strQ.toString(), MedicineDto.class);
            query.setFirstResult(Constants.PAGE_SIZE * (icd10SearchReq.getPage() - 1));
            query.setMaxResults(Constants.PAGE_SIZE);
            Query queryC = entityManager.createQuery(strC.toString());
            if(StringUtils.isNotEmpty(icd10SearchReq.getKey())){
                query.setParameter("name", "%" + icd10SearchReq.getKey() + "%");
                queryC.setParameter("name", "%" + icd10SearchReq.getKey() + "%");
                query.setParameter("unit", "%" + icd10SearchReq.getKey() + "%");
                queryC.setParameter("unit", "%" + icd10SearchReq.getKey() + "%");
            }
            List<MedicineDto> result = query.getResultList();
            long total = (long) queryC.getSingleResult();
            if(result != null){
                Page<MedicineDto> page = new PageImpl<>(result,paging,total);
                response.setItems(result);
                response.setTotalItems((int)total);
                response.setCurrentPage(icd10SearchReq.getPage());
                response.setTotalPages(page.getTotalPages());
                return response;
            }
        } catch (Exception e){
            logger.error("get all with paging with error : {} | {}", e.getMessage(), e.getMessage().toString());
        }
        return null;
    }


    @Override
    public void addEditMedicine(MedicineReq medicineReq) {
        MedicineMaster medicine = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if((medicineReq.getId()) != null){
            Optional<MedicineMaster> medicineUpdate = medicineMasterRepository.findById(medicineReq.getId());
            if(medicineUpdate.isPresent()){
                medicine = medicineUpdate.get();
            }
        }
        if (medicine == null){
            medicine = new MedicineMaster();
        }
        medicine.setName(medicineReq.getName());

        medicine.setUnit(medicineReq.getHamLuong());
        medicine.setSoDangKy(medicineReq.getSoDangKy());
        medicine.setDongGoi(medicineReq.getDongGoi());
        medicine.setHangSX(medicineReq.getHangSX());
        medicine.setNuocSX(medicineReq.getNuocSX());
        medicine = medicineMasterRepository.save(medicine);
        RouteMaster routeMaster = routeMasterRepository.findByCode(medicineReq.getMaDuongDung());
        if(routeMaster != null){
            MedicineRoute medicineRoute = medicineRouteRepository.findByRouteMasterId(routeMaster.getId());
            if(medicineRoute == null){
                medicineRoute = new MedicineRoute();
            }
            medicineRoute.setRouteMasterId(routeMaster.getId());
            medicineRoute.setMedicineMasterId(medicine.getId());
            medicineRouteRepository.save(medicineRoute);
        }else{
            routeMaster = new RouteMaster();
            routeMaster.setCode(medicineReq.getMaDuongDung());
            routeMaster.setName(medicineReq.getDuongDung());
            routeMasterRepository.save(routeMaster);
            MedicineRoute medicineRoute = new MedicineRoute();
            medicineRoute.setRouteMasterId(routeMaster.getId());
            medicineRoute.setMedicineMasterId(medicine.getId());
            medicineRouteRepository.save(medicineRoute);
        }
        List<GenericMaster> genericMasters = genericMasterRepository.findByCode(medicineReq.getMaHoatChat());
        if(genericMasters != null){
            medicine.setGenericMasterCode(medicineReq.getMaHoatChat());
            medicineMasterRepository.save(medicine);
        }else{
            GenericMaster genericMaster = new GenericMaster();
            genericMaster.setCode(medicineReq.getMaHoatChat());
            genericMaster.setName(medicineReq.getHoatChat());
            genericMaster = genericMasterRepository.save(genericMaster);
            medicine.setGenericMasterCode(genericMaster.getCode());
            medicineMasterRepository.save(medicine);
        }

    }

    @Override
    public MedicineReq getDataMedicineById(Long id) {
        MedicineReq medicineReq = new MedicineReq();
        Optional<MedicineMaster> medicineUpdate = medicineMasterRepository.findById(id);
        if(medicineUpdate.isPresent()){
            MedicineMaster medicine = medicineUpdate.get();
            medicineReq.setId(medicine.getId());
            medicineReq.setName(medicine.getName());
            List<GenericMaster> genericMaster = genericMasterRepository.findByCode(medicineUpdate.get().getGenericMasterCode());
            if(genericMaster != null){
                medicineReq.setMaHoatChat(genericMaster.get(0).getCode());
                medicineReq.setHoatChat(genericMaster.get(0).getName());
            }
            MedicineRoute medicineRoute = medicineRouteRepository.findByMedicineMasterId(medicineUpdate.get().getId());
            if(medicineRoute != null){
                Optional<RouteMaster> routeMaster = routeMasterRepository.findById(medicineRoute.getRouteMasterId());
                if(routeMaster.isPresent()){
                    medicineReq.setMaDuongDung(routeMaster.get().getCode());
                    medicineReq.setDuongDung(routeMaster.get().getName());
                }
            }

            medicineReq.setHamLuong(medicine.getUnit());
            medicineReq.setSoDangKy(medicine.getSoDangKy());
            medicineReq.setDongGoi(medicine.getDongGoi());
            medicineReq.setHangSX(medicine.getHangSX());
            medicineReq.setNuocSX(medicine.getNuocSX());
        }
        return medicineReq;
    }
}
