package com.umg.voxel.chequealo.repository;

import com.umg.voxel.chequealo.model.Schedule;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
