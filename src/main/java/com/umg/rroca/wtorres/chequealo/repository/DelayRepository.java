package com.umg.rroca.wtorres.chequealo.repository;

import org.springframework.stereotype.Repository;
import com.umg.rroca.wtorres.chequealo.model.Delay;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DelayRepository extends JpaRepository<Delay, Long> {
}
