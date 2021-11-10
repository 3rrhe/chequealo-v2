package com.umg.voxel.chequealo.repository;

import com.umg.voxel.chequealo.model.Cuser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Cuser, Long> {
    Optional<Cuser> findByUsername(String username);
}
