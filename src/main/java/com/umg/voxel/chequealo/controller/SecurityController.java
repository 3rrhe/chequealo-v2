package com.umg.voxel.chequealo.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import com.umg.voxel.chequealo.model.Cuser;
import com.umg.voxel.chequealo.model.Employee;
import com.umg.voxel.chequealo.utils.AuthUser;
import org.springframework.http.ResponseEntity;
import com.umg.voxel.chequealo.model.JobPosition;
import com.umg.voxel.chequealo.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;
import com.umg.voxel.chequealo.utils.RegisterUser;
import com.umg.voxel.chequealo.utils.ConfigProperty;
import com.umg.voxel.chequealo.service.EncryptService;
import com.umg.voxel.chequealo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import com.umg.voxel.chequealo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.umg.voxel.chequealo.repository.JobPositionRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import com.umg.voxel.chequealo.exception.ResourceNotFoundException;

import java.util.*;
import java.util.List;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class SecurityController {

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

    @Autowired
    public SecurityController(ConfigProperty appConfig) {
        this.secretJWT = appConfig.getJwtSecret();
        this.expirationJWT = appConfig.getJwtExpiration();
        this.pwdSeed = appConfig.getSeed();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody AuthUser user) {
        ApiResponse response;

        try {
            Cuser existingUser =
                    userRepository
                            .findByUsername(user.getUsername())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + user.getUsername()));

            if (existingUser.getPassword().equals(encryptService.encrypt(user.getPassword(), pwdSeed))) {
                user.setRole(existingUser.getRole());
                response = new ApiResponse(HttpStatus.OK.value(), "User logged", loginAction(user));
                return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
            } else {
                response = new ApiResponse(HttpStatus.FORBIDDEN.value(), "Invalid credentials", null);
                return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
            }
        } catch (ResourceNotFoundException e) {
            response = new ApiResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create new user.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterUser user) {
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

            AuthUser authUser = new AuthUser();
            authUser.setUsername(newCuser.getUsername());
            authUser.setPassword(newCuser.getPassword());
            authUser.setRole(newCuser.getRole());

            response = new ApiResponse(HttpStatus.OK.value(), "User created success", loginAction(authUser));
            return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param username the username
     * @return String
     */
    private String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("chequealoJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getDateExpiration(this.expirationJWT))
                .signWith(SignatureAlgorithm.HS512,
                        this.secretJWT.getBytes()).compact();

        return "Bearer " + token;
    }

    /**
     * @param minutes the minutes
     * @return Date
     */
    private static Date getDateExpiration(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }

    /**
     * @param user the authUser
     * @return AuthUser
     */
    private AuthUser loginAction(AuthUser user) {
        String token = getJWTToken(user.getUsername());
        user.setBearer(token);

        return user;
    }
}