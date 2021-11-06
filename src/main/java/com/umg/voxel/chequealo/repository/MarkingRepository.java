package com.umg.voxel.chequealo.repository;

import org.springframework.data.jdbc.repository.support.SimpleJdbcRepository;
import org.springframework.stereotype.Repository;
import com.umg.voxel.chequealo.model.Profile;
import com.umg.voxel.chequealo.model.Marking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface MarkingRepository extends SimpleJdbcRepository<Marking, Long> {
    List<Marking> findAllByProfile(Profile profile);
}
