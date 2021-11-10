package com.umg.voxel.chequealo.repository;

import com.umg.voxel.chequealo.model.UserOauth;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserOauthRepository extends JpaRepository<UserOauth, Long> {
}
