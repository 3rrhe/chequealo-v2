package com.umg.voxel.chequealo.repository;

import com.umg.voxel.chequealo.model.Department;
import org.springframework.stereotype.Repository;
import com.umg.voxel.chequealo.model.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
    List<JobPosition> findAllByDepartment(Department department);
}
