package com.umg.voxel.chequealo.repository;

import com.umg.voxel.chequealo.model.Cuser;
import com.umg.voxel.chequealo.model.Employee;
import com.umg.voxel.chequealo.model.JobPosition;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByCuser(Cuser cuser);

    List<Employee> findAllByJobPosition(JobPosition jobPosition);

    Optional<Employee> findByCuser(Cuser cuser);
}
