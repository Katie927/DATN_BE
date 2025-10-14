package com.DATN.Bej.repository.workSchedule;

import com.DATN.Bej.entity.work.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<WorkSchedule, String> {
    @Query(value = """
    SELECT * FROM work_schedule
    WHERE work_date BETWEEN :startDate AND :endDate
    ORDER BY work_date
    """, nativeQuery = true)
    List<WorkSchedule> findByDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
