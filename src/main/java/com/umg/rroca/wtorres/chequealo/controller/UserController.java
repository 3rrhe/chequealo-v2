package com.umg.rroca.wtorres.chequealo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.umg.rroca.wtorres.chequealo.model.User;
import com.umg.rroca.wtorres.chequealo.model.Profile;
import com.umg.rroca.wtorres.chequealo.utils.ApiResponse;
import com.umg.rroca.wtorres.chequealo.utils.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import com.umg.rroca.wtorres.chequealo.repository.UserRepository;
import com.umg.rroca.wtorres.chequealo.repository.ProfileRepository;
import com.umg.rroca.wtorres.chequealo.exception.ResourceNotFoundException;

import java.util.Map;
import java.util.HashMap;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    /**
     * Get all User list.
     *
     * @return the list
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        ApiResponse res;

        try {
            res = new ApiResponse(HttpStatus.OK.value(), "Profiles list", profileRepository.findAll());
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets clients by id.
     *
     * @param userId the userId
     * @return the user by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> getUsersById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        ApiResponse res;

        try {
            User user =
                    userRepository
                            .findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

            Profile profile = profileRepository
                    .findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + userId));

            res = new ApiResponse(HttpStatus.OK.value(), "User profile", profile);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets clients by username.
     *
     * @param username the username
     * @return the user by username
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/users/{username}")
    public ResponseEntity<ApiResponse> getUsersByUsername(@PathVariable(value = "username") String username)
            throws ResourceNotFoundException {
        ApiResponse res;

        try {
            User user =
                    userRepository
                            .findByUsername(username)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + username));

            Profile profile = profileRepository
                    .findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + username));

            res = new ApiResponse(HttpStatus.OK.value(), "User profile", profile);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update user profile entity.
     *
     * @param userId      the user id
     * @param userDetails the user details
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable(value = "id") Long userId, @Valid @RequestBody RegisterUser userDetails)
            throws ResourceNotFoundException {
        ApiResponse res;

        try {
            User user =
                    userRepository
                            .findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

            Profile profile = profileRepository.findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + userId));

            profile.setFirstName(userDetails.getFirstName());
            profile.setLastName(userDetails.getLastName());
            profile.setAddress(userDetails.getAddress());

            final Profile updatedProfile = profileRepository.save(profile);

            res = new ApiResponse(HttpStatus.OK.value(), "Update user profile", updatedProfile);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete user map.
     *
     * @param userId the user id
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + userId));

        profileRepository.delete(profile);
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}