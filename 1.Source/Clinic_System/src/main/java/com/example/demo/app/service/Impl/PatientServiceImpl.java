package com.example.demo.app.service.Impl;

import com.example.demo.app.entity.*;
import com.example.demo.app.repository.*;
import com.example.demo.app.service.AdmissionRecordService;
import com.example.demo.app.service.PatientService;
import com.example.demo.app.service.ServiceService;
import com.example.demo.app.vo.*;
import com.example.demo.catalog.repository.MedicineMasterRepository;
import com.example.demo.catalog.request.MedicineReq;
import com.example.demo.catalog.request.ServiceRes;
import com.example.demo.catalog.service.DiseaseListIcd10Service;
import com.example.demo.common.CommonRes;
import com.example.demo.common.Constants;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import com.example.demo.user.vo.UserSearchReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserService userService;

    @Autowired
    DiseaseListIcd10Service diseaseListIcd10Service;

    @Autowired
    ServiceService serviceService;

    @Autowired
    private PatientDiagnosisRepository patientDiagnosisRepository;

    @Autowired
    AdmissionRecordRepository admissionRecordRepository;

    @Autowired
    PrescriptionOfPatientRepository prescriptionOfPatientRepository;

    @Autowired
    private MedicineMasterRepository medicineMasterRepository;

    @Autowired
    private RouteMasterRepository routeMasterRepository;

    @Autowired
    private GenericMasterRepository genericMasterRepository;

    @Autowired
    private MedicineRouteRepository medicineRouteRepository;

    @Override
    public PatientDetailDto getPatientById(Long id) {
        PatientDetailDto patientDetailDto = new PatientDetailDto();
        Optional<Patient> patients = patientRepository.findById(id);
        if(patients.isPresent()){
            Patient patient = patients.get();
            User user = userService.findById(patient.getUserID());
            patientDetailDto.setId(patient.getId());
            patientDetailDto.setCode(user.getId() + "");
            patientDetailDto.setName(user.getName());
            patientDetailDto.setAddress(user.getAddress());
            patientDetailDto.setBirthday(user.getBirthday());
            patientDetailDto.setGender(user.getGender());
            patientDetailDto.setPhone(user.getPhoneNumber());
            patientDetailDto.setHeight(patient.getHeight());
            patientDetailDto.setBloodPressure(patient.getBloodPressure());
            patientDetailDto.setWeight(patient.getWeight());
            patientDetailDto.setTemperature(patient.getTemperature());
            patientDetailDto.setDayExamination(patient.getDayExamination());
            if(patient.getFirstDiagnosisId() != null){
                DiseaseListICD10 diseaseListICD10 = diseaseListIcd10Service.findById(patient.getFirstDiagnosisId());
                if(diseaseListICD10 != null){
                    patientDetailDto.setFirstDiagnosis(diseaseListICD10.getDiseaseName());
                    patientDetailDto.setFirstDiagnosisId(diseaseListICD10.getId());
                }
            }
            if(patient.getFinalDiagnosisId() != null){
                DiseaseListICD10 diseaseListICD102 = diseaseListIcd10Service.findById(patient.getFinalDiagnosisId());
                if(diseaseListICD102 != null){
                    patientDetailDto.setFinalDiagnosis(diseaseListICD102.getDiseaseName());
                    patientDetailDto.setFinalDiagnosisId(diseaseListICD102.getId());
                }
            }

            List<PatientDiagnosis> patientDiagnoses = patientDiagnosisRepository.findByPatientId(patient.getId());
            if(patientDiagnoses != null){
                List<Long> includingDiseasesId = new ArrayList<>();
                List<String> includingDiseases = new ArrayList<>();
                for (PatientDiagnosis patientDiagnosis: patientDiagnoses){
                    DiseaseListICD10 diseaseListICD103 = diseaseListIcd10Service.findById(patientDiagnosis.getDiseaseListICD10Id());
                    if(diseaseListICD103 != null){
                        includingDiseases.add(diseaseListICD103.getDiseaseName());
                        includingDiseasesId.add(diseaseListICD103.getId());
                    }
                }
                patientDetailDto.setIncludingDiseases(includingDiseases.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",")));
                patientDetailDto.setIncludingDiseasesId(includingDiseasesId.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",")));
            }
            patientDetailDto.setConclude(patient.getConclude());
            patientDetailDto.setSymptom(patient.getSymptom());
            return patientDetailDto;
        }
        return null;
    }

    @Override
    public void updatePatient(PatientDetailDto updatePatient) {
        Optional<Patient> patients = patientRepository.findById(updatePatient.getId());
        if(patients.isPresent()){
            Patient patient = patients.get();
            patient.setSymptom(updatePatient.getSymptom());
            if(updatePatient.getFirstDiagnosisId() != null){
                patient.setFirstDiagnosisId(updatePatient.getFirstDiagnosisId());
            }
            if(updatePatient.getFinalDiagnosisId() != null){
                patient.setFinalDiagnosisId(updatePatient.getFinalDiagnosisId());
            }
            if(StringUtils.isNotEmpty(updatePatient.getIncludingDiseasesId())){
                String[] ids = updatePatient.getIncludingDiseasesId().split(",");
                for (String id:ids){
                    if(StringUtils.isNotEmpty(id)){
                        DiseaseListICD10 diseaseListICD10 = diseaseListIcd10Service.findById(Long.parseLong(id));
                        if(diseaseListICD10 != null){
                            PatientDiagnosis patientDiagnosis =
                                    patientDiagnosisRepository.findByPatientIdAndDiseaseListICD10Id(patient.getId(), diseaseListICD10.getId());
                            if (patientDiagnosis == null){
                                patientDiagnosis = new PatientDiagnosis();
                            }
                            patientDiagnosis.setPatientId(patient.getId());
                            patientDiagnosis.setDiseaseListICD10Id(diseaseListICD10.getId());
                            patientDiagnosisRepository.save(patientDiagnosis);
                        }
                    }
                }
            }
            patient.setConclude(updatePatient.getConclude());
            patient.setModifiedTime(new Date());
            patient.setTemperature(updatePatient.getTemperature());
            patient.setBloodPressure(updatePatient.getBloodPressure());
            patient.setWeight(updatePatient.getWeight());
            patient.setHeight(updatePatient.getHeight());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            patient.setModifiedUser(user.getName());
            patientRepository.save(patient);
        }
    }

    @Override
    public Patient findById(Long id) {

        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()){
            return patient.get();
        }
        return null;
    }

    @Override
    public CommonRes getPatient(PatientReq patientReq) {
        CommonRes commonRes = new CommonRes();
        int page = 1;
        if(patientReq.getPage() != null){
            page = patientReq.getPage();
        }
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, Constants.PAGE_SIZE);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userLogin= (User) auth.getPrincipal();
        String key = "";
        if(StringUtils.isNotEmpty(patientReq.getKey())){
            key = patientReq.getKey();
        }
        Page<Patient> patients = patientRepository.getDataByName(key, userLogin.getId(), pageable);
        if(patients != null){
            PatientListRes patientListRes = new PatientListRes();
            patientListRes.setTotal(patients.getTotalElements());
            List<PatientDetailDto> patientDetailDtoList = new ArrayList<>();
            for (Patient user:patients){
                PatientDetailDto patientDetailDto = new PatientDetailDto();
                User userPatient = userService.findById(user.getUserID());
                if(userPatient != null){
                    patientDetailDto.setId(userPatient.getId());
                    patientDetailDto.setName(userPatient.getName());
                    patientDetailDto.setGender(userPatient.getGender());
                    patientDetailDto.setBirthday(userPatient.getBirthday());
                    patientDetailDto.setPhone(userPatient.getPhoneNumber());
                }

                patientDetailDto.setCode(user.getUserID() + "");
                patientDetailDtoList.add(patientDetailDto);
            }
            patientListRes.setPatientDetailDtos(patientDetailDtoList);
            commonRes.setData(patientListRes);
        }
        return commonRes;
    }

    @Override
    public CommonRes getPatientRecord(Long id) {
        CommonRes commonRes = new CommonRes();
        User user = userService.findById(id);
        PatientRecordRes recordRes = new PatientRecordRes();
        if(user != null){
            recordRes.setId(user.getId());
            recordRes.setName(user.getName());
            recordRes.setCode(user.getId() + "");
            recordRes.setAddress(user.getAddress());
            recordRes.setPhone(user.getPhoneNumber());
            recordRes.setGender(user.getGender());
            List<Patient> patients = patientRepository.findAllByUserId(user.getId());
            recordRes.setPatients(patients);
            commonRes.setData(recordRes);
        }
        return commonRes;
    }

    @Override
    public ProfilePatientDto getProfilePatientById(Long id) {
        ProfilePatientDto patientDetailDto = new ProfilePatientDto();
        User user = userService.findById(id);
        if(user != null){
            patientDetailDto.setId(user.getId());
            patientDetailDto.setCode(user.getId() + "");
            patientDetailDto.setName(user.getName());
            patientDetailDto.setAddress(user.getAddress());
            patientDetailDto.setBirthday(user.getBirthday());
            patientDetailDto.setGender(user.getGender());
            patientDetailDto.setPhone(user.getPhoneNumber());
            List<Patient> patients = patientRepository.findAllByUserId(user.getId());
            patientDetailDto.setPatients(patients);
            return patientDetailDto;
        }
        return null;
    }

    @Override
    public CommonRes getPaymentPatientRecord(UserSearchReq userSearchReq) {
        CommonRes commonRes = new CommonRes();
        int page = 1;
        if(userSearchReq.getPage() != null){
            page = userSearchReq.getPage();
        }
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, Constants.PAGE_SIZE);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userLogin= (User) auth.getPrincipal();
        String key = "";
        if(StringUtils.isNotEmpty(userSearchReq.getKey())){
            key = userSearchReq.getKey();
        }
        Page<Patient> patients = patientRepository.getDataByPhone(key, pageable);
        if(patients != null){
            PaymentRes paymentRes = new PaymentRes();
            paymentRes.setTotal(patients.getTotalElements());
            List<PaymentDto> paymentDtos = new ArrayList<>();
            for (Patient user:patients){
                PaymentDto paymentDto = new PaymentDto();
                User userPatient = userService.findById(user.getUserID());
                if(userPatient != null){
                    paymentDto.setCode(userPatient.getId()+"");
                    paymentDto.setFullName(userPatient.getName());
                    paymentDto.setPhone(userPatient.getPhoneNumber());
                }
                paymentDto.setIdPatient(user.getId());
                paymentDto.setStatusPayment(user.getStatusPayment());
                paymentDto.setAdmissionDate(user.getDayExamination());
                paymentDtos.add(paymentDto);
            }
            paymentRes.setPaymentDtos(paymentDtos);
            commonRes.setData(paymentRes);
        }
        return commonRes;
    }

    @Override
    public void getPaymentPatientChangeStatus(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User userLogin= (User) auth.getPrincipal();
            Patient patientOld = patient.get();
            patientOld.setStatusPayment(Constants.STATUS_PAYMENTED);
            patientOld.setModifiedTime(new Date());
            patientOld.setModifiedUser(userLogin.getName());
            patientRepository.save(patientOld);
        }
    }

    @Override
    public CommonRes getPaymentPatientRecordById(Long id) {
        CommonRes commonRes = new CommonRes();
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()){
            PaymentDto paymentDto = new PaymentDto();
            User user = userService.findById(patient.get().getUserID());
            if(user != null){
                paymentDto.setCode(user.getId()+"");
                paymentDto.setFullName(user.getName());
                paymentDto.setPhone(user.getPhoneNumber());
                paymentDto.setGender(user.getGender());
                paymentDto.setOld(calculateAge(user.getBirthday(), new Date()));
                paymentDto.setAddress(user.getAddress());
            }
            paymentDto.setIdPatient(patient.get().getId());
            List<ServiceRes> services = serviceService.getServiceByPatientId(patient.get().getId());
            paymentDto.setServices(services);
            commonRes.setData(paymentDto);
        }
        return commonRes;
    }

    @Override
    public ProfileDetailPatientDto getProfilePatientDetailByPatientId(Long id) {
        ProfileDetailPatientDto profileDetailPatientDto = new ProfileDetailPatientDto();
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isPresent()){
            profileDetailPatientDto.setIdPatient(patient.get().getId());
            profileDetailPatientDto.setDayExamination(patient.get().getDayExamination());
            if(patient.get().getFirstDiagnosisId() != null){
                DiseaseListICD10 diseaseListICD101 = diseaseListIcd10Service.findById(patient.get().getFirstDiagnosisId());
                if(diseaseListICD101 != null){
                    profileDetailPatientDto.setFirstDiagnosis(diseaseListICD101.getDiseaseName());
                }
            }
            if(patient.get().getFinalDiagnosisId() != null){
                DiseaseListICD10 diseaseListICD102 = diseaseListIcd10Service.findById(patient.get().getFinalDiagnosisId());
                if(diseaseListICD102 != null){
                    profileDetailPatientDto.setFinalDiagnosis(diseaseListICD102.getDiseaseName());
                }
            }
            profileDetailPatientDto.setConclude(patient.get().getConclude());
            profileDetailPatientDto.setTemperature(patient.get().getTemperature());
            profileDetailPatientDto.setHeight(patient.get().getHeight());
            profileDetailPatientDto.setWeight(patient.get().getWeight());
            profileDetailPatientDto.setBloodPressure(patient.get().getBloodPressure());
            profileDetailPatientDto.setSymptom(patient.get().getSymptom());
            User user = userService.findById(patient.get().getUserID());
            if(user != null){
                profileDetailPatientDto.setName(user.getName());
                profileDetailPatientDto.setBirthday(user.getBirthday());
                profileDetailPatientDto.setGender(user.getGender());
                profileDetailPatientDto.setPhone(user.getPhoneNumber());
                profileDetailPatientDto.setAddress(user.getAddress());
            }
            List<AdmissionRecord> admissionRecord = admissionRecordRepository.findByPatientID(id);
            if(admissionRecord != null){
                User user2 = userService.findById(admissionRecord.get(0).getDoctorId());
                if(user2 != null){
                    profileDetailPatientDto.setDoctorName(user2.getName());
                }
            }
            List<MedicineReq> medicineReqs = new ArrayList<>();
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
                        medicineReqs.add(medicineReq);
                    }
                }
            }
            profileDetailPatientDto.setPrescriptionReqList(medicineReqs);

        }
        return profileDetailPatientDto;
    }

    public int calculateAge(Date birthDate,Date currentDate) {
        return Period.between(convertToLocalDateViaMilisecond(birthDate),
                convertToLocalDateViaMilisecond(currentDate)).getYears();
    }
    public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
