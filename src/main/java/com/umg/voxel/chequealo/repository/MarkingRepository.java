package com.umg.voxel.chequealo.repository;

import com.umg.voxel.chequealo.model.Marking;
import com.umg.voxel.chequealo.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface MarkingRepository extends JpaRepository<Marking, Long> {
    List<Marking> findAllByEmployee(Employee employee);
}
