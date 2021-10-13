package com.umg.rroca.wtorres.chequealo.repository;

import org.springframework.stereotype.Repository;
import com.umg.rroca.wtorres.chequealo.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
