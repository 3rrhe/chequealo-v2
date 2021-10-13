package com.umg.rroca.wtorres.chequealo.repository;

import org.springframework.stereotype.Repository;
import com.umg.rroca.wtorres.chequealo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
