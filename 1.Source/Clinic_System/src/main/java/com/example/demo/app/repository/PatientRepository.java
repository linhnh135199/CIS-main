package com.example.demo.app.repository;

import com.example.demo.app.entity.Patient;
import com.example.demo.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

    @Query("select p from User u join Patient p on u.id = p.userID join AdmissionRecord a on p.id = a.patientID where " +
            "u.name like %:key% and a.doctorId = :doctorId or u.phoneNumber like %:key% and a.doctorId = :doctorId")
    Page<Patient> getDataByName(@Param("key") String key, @Param("doctorId") Long doctorId, Pageable pageable);

    @Query("select p from Patient p where p.userID = :userID")
    List<Patient> findAllByUserId(@Param("userID") Long userID);

    @Query("select p from User u join Patient p on u.id = p.userID where u.phoneNumber like %:key% or u.name like %:key%")
    Page<Patient> getDataByPhone(@Param("key") String key, Pageable pageable);

}
