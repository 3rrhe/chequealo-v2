package com.umg.voxel.chequealo.repository;

import org.springframework.stereotype.Repository;
import com.umg.voxel.chequealo.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
