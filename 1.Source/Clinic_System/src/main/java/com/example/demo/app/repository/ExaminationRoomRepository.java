package com.example.demo.app.repository;

import com.example.demo.app.entity.ExaminationRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExaminationRoomRepository extends CrudRepository<ExaminationRoom, Long> {

    @Query("select e from ExaminationRoom e where e.doctorId is not null and e.date BETWEEN :startDate AND :endDate")
    public List<ExaminationRoom> getByDateAndNotDoctor(@Param("startDate") Date startDate,
                                                       @Param("endDate") Date endDate);

    @Query("select e from ExaminationRoom e")
    public List<ExaminationRoom> getAllRoom();

    @Query("select e from ExaminationRoom e where e.doctorId = :doctorId and e.date BETWEEN :startDate AND :endDate")
    public ExaminationRoom getByDateAndDoctor(@Param("doctorId") Long doctorId, @Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate);

    @Query("select e from ExaminationRoom e where e.date BETWEEN :startDate AND :endDate")
    public List<ExaminationRoom> getCalenderDoctor(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("select e from ExaminationRoom e where e.date >= :startDate AND e.date <= :endDate and e.roomCategoryId = :roomId and e.hourAdmission = :hourAdmission")
    public List<ExaminationRoom> getByDateAndRoom(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                                  @Param("roomId") Long roomId, @Param("hourAdmission") String hourAdmission);
}
