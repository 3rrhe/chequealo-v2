package com.umg.voxel.chequealo.repository;

import com.umg.voxel.chequealo.model.JobPosition;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
}
