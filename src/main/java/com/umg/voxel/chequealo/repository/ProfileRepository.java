package com.umg.voxel.chequealo.repository;

import com.umg.voxel.chequealo.model.User;
import org.springframework.stereotype.Repository;
import com.umg.voxel.chequealo.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findAllByUser(User user);

    Optional<Profile> findByUser(User user);
}
