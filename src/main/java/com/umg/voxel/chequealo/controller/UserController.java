package com.umg.voxel.chequealo.controller;

import com.umg.voxel.chequealo.model.JobPosition;
import com.umg.voxel.chequealo.repository.JobPositionRepository;
import com.umg.voxel.chequealo.service.EncryptService;
import com.umg.voxel.chequealo.utils.AuthUser;
import org.springframework.http.HttpStatus;
import com.umg.voxel.chequealo.model.Cuser;
import com.umg.voxel.chequealo.model.Employee;
import org.springframework.http.ResponseEntity;
import com.umg.voxel.chequealo.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;
import com.umg.voxel.chequealo.utils.RegisterUser;
import com.umg.voxel.chequealo.repository.UserRepository;
import com.umg.voxel.chequealo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.umg.voxel.chequealo.exception.ResourceNotFoundException;

import java.util.Map;
import java.util.HashMap;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository profileRepository;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Autowired
    EncryptService encryptService;

    private String secretJWT;
    private int expirationJWT;
    private String pwdSeed;

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
     * Create new user.
     *
     * @return the user
     */
    @PostMapping("/users")
    public ResponseEntity<ApiResponse> createNewUser(@Valid @RequestBody RegisterUser user) {
        ApiResponse response;

        try {
            String email = user.getEmail();
            String[] arrOfStr = email.split("@");

            // Creating new user
            Cuser newCuser = new Cuser();
            newCuser.setUsername(arrOfStr[0]);
            newCuser.setPassword(encryptService.encrypt(user.getPassword(), pwdSeed));
            newCuser.setEmail(email);
            newCuser.setRole(user.getRole());
            newCuser = userRepository.save(newCuser);

            if (newCuser.getRole().equals(Cuser.ROLE_CLIENT)) {
                JobPosition jobPosition = jobPositionRepository
                        .findById(1L)
                        .orElseThrow(() -> new ResourceNotFoundException("Job Position not found for :: " + user.getUsername()));

                // Creating user employee
                Employee newEmployee = new Employee();
                newEmployee.setFirstName(user.getFirstName());
                newEmployee.setLastName(user.getLastName());
                newEmployee.setAddress(user.getAddress());
                newEmployee.setUser(newCuser);

                newEmployee.setJobPosition(jobPosition);

                profileRepository.save(newEmployee);
            }

            response = new ApiResponse(HttpStatus.OK.value(), "User created success", newCuser);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
            Cuser cuser =
                    userRepository
                            .findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

            Employee employee = profileRepository
                    .findByCuser(cuser)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + userId));

            res = new ApiResponse(HttpStatus.OK.value(), "User employee", employee);
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
    @GetMapping("/profiles/{username}")
    public ResponseEntity<ApiResponse> getUsersByUsername(@PathVariable(value = "username") String username)
            throws ResourceNotFoundException {
        ApiResponse res;

        try {
            Cuser cuser =
                    userRepository
                            .findByUsername(username)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + username));

            Employee employee = profileRepository
                    .findByCuser(cuser)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + username));

            res = new ApiResponse(HttpStatus.OK.value(), "User employee", employee);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update user employee entity.
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
            Cuser cuser =
                    userRepository
                            .findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

            Employee employee = profileRepository.findByCuser(cuser)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + userId));

            employee.setFirstName(userDetails.getFirstName());
            employee.setLastName(userDetails.getLastName());
            employee.setAddress(userDetails.getAddress());

            final Employee updatedEmployee = profileRepository.save(employee);

            res = new ApiResponse(HttpStatus.OK.value(), "Update user employee", updatedEmployee);
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
        Cuser cuser =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

        Employee employee = profileRepository.findByCuser(cuser)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + userId));

        profileRepository.delete(employee);
        userRepository.delete(cuser);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}