package com.umg.rroca.wtorres.chequealo.repository;

import org.springframework.stereotype.Repository;
import com.umg.rroca.wtorres.chequealo.model.Marking;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MarkingRepository extends JpaRepository<Marking, Long> {
}
