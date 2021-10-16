package com.umg.rroca.wtorres.chequealo.controller;

import com.umg.rroca.wtorres.chequealo.model.Schedule;
import com.umg.rroca.wtorres.chequealo.repository.ScheduleRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.umg.rroca.wtorres.chequealo.model.User;
import com.umg.rroca.wtorres.chequealo.model.Profile;
import com.umg.rroca.wtorres.chequealo.utils.AuthUser;
import com.umg.rroca.wtorres.chequealo.utils.ApiResponse;
import com.umg.rroca.wtorres.chequealo.utils.RegisterUser;
import org.springframework.security.core.GrantedAuthority;
import com.umg.rroca.wtorres.chequealo.utils.ConfigProperty;
import com.umg.rroca.wtorres.chequealo.service.EncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import com.umg.rroca.wtorres.chequealo.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import com.umg.rroca.wtorres.chequealo.repository.ProfileRepository;
import com.umg.rroca.wtorres.chequealo.exception.ResourceNotFoundException;

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
    private ProfileRepository profileRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

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
            User existingUser =
                    userRepository
                            .findByUsername(user.getUsername())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + user.getUsername()));

            if (existingUser.getPassword().equals(encryptService.encrypt(user.getPassword(), pwdSeed))) {
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
            User newUser = new User();
            newUser.setUsername(arrOfStr[0]);
            newUser.setPassword(encryptService.encrypt(user.getPassword(), pwdSeed));
            newUser.setEmail(email);
            newUser = userRepository.save(newUser);

            // Creating user profile
            Profile newProfile = new Profile();
            newProfile.setFirstName(user.getFirstName());
            newProfile.setLastName(user.getLastName());
            newProfile.setAddress(user.getAddress());
            newProfile.setUserId(newUser);

            Schedule schedule;

            switch(newUser.getRole()) {
                case "ROLE_BOSS":
                    schedule = scheduleRepository.getById(2L);
                    break;
                case "ROLE_SECURITY":
                    schedule = scheduleRepository.getById(3L);
                    break;
                case "ROLE_USER":
                default:
                    schedule = scheduleRepository.getById(1L);
            }

            newProfile.setSchedule(schedule);

            newProfile = profileRepository.save(newProfile);

            AuthUser authUser = new AuthUser();
            authUser.setUsername(newUser.getUsername());
            authUser.setPassword(newUser.getPassword());

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