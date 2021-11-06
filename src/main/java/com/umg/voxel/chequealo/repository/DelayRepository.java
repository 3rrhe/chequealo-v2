package com.umg.voxel.chequealo.repository;

import org.springframework.stereotype.Repository;
import com.umg.voxel.chequealo.model.Delay;
import com.umg.voxel.chequealo.model.Marking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface DelayRepository extends JpaRepository<Delay, Long> {
    List<Delay> findAllByMarking(Marking marking);
}
