package com.example.demo.app.service.Impl;

import com.example.demo.app.entity.*;
import com.example.demo.app.repository.*;
import com.example.demo.app.service.PrescriptionOfPatientService;
import com.example.demo.app.vo.PatientPrescriptionReq;
import com.example.demo.app.vo.PrescriptionReq;
import com.example.demo.catalog.repository.MedicineMasterRepository;
import com.example.demo.catalog.request.MedicineReq;
import com.example.demo.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionOfPatientServiceImpl implements PrescriptionOfPatientService {

    @Autowired
    private PrescriptionOfPatientRepository prescriptionOfPatientRepository;

    @Autowired
    private MedicineMasterRepository medicineMasterRepository;

    @Autowired
    RegimenMedicineRepository regimenMedicineRepository;

    @Autowired
    private RouteMasterRepository routeMasterRepository;
    @Autowired
    private GenericMasterRepository genericMasterRepository;
    @Autowired
    private MedicineRouteRepository medicineRouteRepository;

    @Override
    public List<MedicineReq> getPrescriptionOfPatientById(Long id) {
        List<MedicineReq> medicines = new ArrayList<>();
        List<PrescriptionOfPatient> patients = prescriptionOfPatientRepository.findAllByPatientId(id);
        if(patients != null){
            for (PrescriptionOfPatient prescriptionOfPatient: patients){
                Optional<MedicineMaster> medicine = medicineMasterRepository.findById(prescriptionOfPatient.getMedicineId());
                MedicineReq medicineReq = new MedicineReq();
                medicineReq.setDate(prescriptionOfPatient.getCreatedTime());
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
                    medicineReq.setHuongDan(prescriptionOfPatient.getHuongDan());
                    medicineReq.setDonVi(prescriptionOfPatient.getDonVi());
                    medicineReq.setSoLuong(prescriptionOfPatient.getSoLuong());
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
        }
        return medicines;
    }

    @Override
    public void updatePrescriptionToPatient(PatientPrescriptionReq patientPrescriptionReq) {
        List<PrescriptionOfPatient> patients = prescriptionOfPatientRepository.findAllByPatientId(patientPrescriptionReq.getIdPatient());
        if(patients != null){
            for (PrescriptionOfPatient patient: patients){
                prescriptionOfPatientRepository.delete(patient);
            }
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        for (PrescriptionReq prescriptionReq:patientPrescriptionReq.getPrescriptionReqList()){
            Optional<MedicineMaster> medicine = medicineMasterRepository.findById(prescriptionReq.getId());
            if(medicine.isPresent()){
                PrescriptionOfPatient prescriptionOfPatient = new PrescriptionOfPatient();
                prescriptionOfPatient.setMedicineId(medicine.get().getId());
                prescriptionOfPatient.setPatientId(patientPrescriptionReq.getIdPatient());
                prescriptionOfPatient.setCreatedTime(new Date());
                prescriptionOfPatient.setCreatedUser(user.getName());
                prescriptionOfPatient.setDoctorId(user.getId());
                prescriptionOfPatient.setSoLuong(prescriptionReq.getSoLuong());
                prescriptionOfPatient.setDonVi(prescriptionReq.getDonVi());
                prescriptionOfPatient.setHuongDan(prescriptionReq.getHuongDan());
                prescriptionOfPatientRepository.save(prescriptionOfPatient);
            }
        }
    }

    @Override
    public List<MedicineReq> getPrescriptionByRegimen(Long id) {
        List<MedicineReq> medicines = new ArrayList<>();
        List<RegimenMedicine> regimenMedicines = regimenMedicineRepository.findAllByRegimenId(id);
        for (RegimenMedicine mi: regimenMedicines){
            Optional<MedicineMaster> medicine = medicineMasterRepository.findById(mi.getMedicineId());
            MedicineReq medicineReq = new MedicineReq();
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
                medicines.add(medicineReq);
            }
        }
        return medicines;
    }
}
