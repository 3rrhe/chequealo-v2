package com.umg.rroca.wtorres.chequealo.repository;

import org.springframework.stereotype.Repository;
import com.umg.rroca.wtorres.chequealo.model.Profile;
import com.umg.rroca.wtorres.chequealo.model.Marking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkingRepository extends JpaRepository<Marking, Long> {
    List<Marking> findAllByProfile(Profile profile);
}
