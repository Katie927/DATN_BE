package com.DATN.Bej.repository.workSchedule;

import com.DATN.Bej.entity.work.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

}
