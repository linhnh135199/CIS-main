package com.example.demo.app.repository;

import com.example.demo.app.entity.AdmissionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdmissionRecordRepository extends CrudRepository<AdmissionRecord, Long> {

    @Query("select a from AdmissionRecord a join Patient p on p.id = a.patientID join User u on u.id = p.userID where a.doctorId = :doctorId and a.statusApprove = 'receive' and a.admissionDate BETWEEN :startDate AND :endDate")
    List<AdmissionRecord> findAllByDoctorCurDate(@Param("doctorId") Long doctorId, @Param("startDate") Date startDate, @Param("endDate")Date endDate);

    @Query("select a from AdmissionRecord a join Patient p on p.id = a.patientID join User u on u.id = p.userID where u.name like %:key% and a.statusApprove != 'receive' or u.phoneNumber like %:key% and a.statusApprove != 'receive'")
    Page<AdmissionRecord> getAllAdmission(@Param("key") String key, Pageable pageable);

    List<AdmissionRecord> findAllByPatientID(Long id);

    List<AdmissionRecord> findByPatientID(Long id);
}
