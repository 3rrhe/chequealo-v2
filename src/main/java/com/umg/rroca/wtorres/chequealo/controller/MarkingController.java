package com.umg.rroca.wtorres.chequealo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.umg.rroca.wtorres.chequealo.model.*;
import org.springframework.web.bind.annotation.*;
import com.umg.rroca.wtorres.chequealo.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.umg.rroca.wtorres.chequealo.repository.UserRepository;
import com.umg.rroca.wtorres.chequealo.repository.DelayRepository;
import com.umg.rroca.wtorres.chequealo.repository.MarkingRepository;
import com.umg.rroca.wtorres.chequealo.repository.ProfileRepository;
import com.umg.rroca.wtorres.chequealo.exception.ResourceNotFoundException;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class MarkingController {

    @Autowired
    private MarkingRepository markingRepository;

    @Autowired
    private DelayRepository delayRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all marking list.
     *
     * @return the list
     */
    @GetMapping("/markings")
    public ResponseEntity<ApiResponse> getAllMarkings() {
        ApiResponse res;

        try {
            res = new ApiResponse(HttpStatus.OK.value(), "Markings list", markingRepository.findAll());
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all profile marking list.
     *
     * @param username the profileId
     * @return the list
     */
    @GetMapping("/profiles/{username}/markings")
    public ResponseEntity<ApiResponse> getAllProfileMarkings(@PathVariable(value = "username") String username) {
        ApiResponse res;

        try {
            User user =
                    userRepository
                            .findByUsername(username)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + username));

            Profile profile = profileRepository
                    .findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + username));

            List<Marking> markings = markingRepository.findAllByProfile(profile);

            res = new ApiResponse(HttpStatus.OK.value(), "Profile markings list", markings);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets markings by id.
     *
     * @param markingId the markingId
     * @return the marking by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/markings/{id}")
    public ResponseEntity<ApiResponse> getMarkingsById(@PathVariable(value = "id") Long markingId)
            throws ResourceNotFoundException {
        ApiResponse res;

        try {
            Marking marking =
                    markingRepository
                            .findById(markingId)
                            .orElseThrow(() -> new ResourceNotFoundException("Marking not found on :: " + markingId));

            res = new ApiResponse(HttpStatus.OK.value(), "Marking details", marking);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create new schedule.
     *
     * @param username the profileId
     * @return the schedule
     */
    @PostMapping("/profiles/{username}/markings")
    public ResponseEntity<ApiResponse> createSchedule(@PathVariable(value = "username") String username) {
        ApiResponse res;

        try {
            Marking marking;
            User user =
                    userRepository
                            .findByUsername(username)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + username));

            Profile profile = profileRepository
                    .findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + username));

            Schedule schedule = profile.getSchedule();

            List<Marking> markings = markingRepository
                    .findAllByProfile(profile);

            if (markings.size() <= 0) {
                marking = new Marking();
            } else {
                marking = markings.get(markings.size() - 1);
            }

            if (marking.getEntryAt() != null) { // If marking has entryAt data
                String markingCreatedAt = new SimpleDateFormat("dd/MM/yyyy").format(marking.getEntryAt());
                String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

                if (markingCreatedAt.equals(today)) { // Check if entryAt is today's date
                    res = new ApiResponse(HttpStatus.OK.value(), "You already marked your entry time today", marking);
                    return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
                } else { // Check if entryAt is not today's date
                    marking = new Marking();
                }
            }

            marking.setProfile(profile);
            marking.setEntryAt(new Date());
            String todayTime = new SimpleDateFormat("HH:mm:ss").format(marking.getEntryAt());

            markingRepository.save(marking);
            List<Delay> delays = delayRepository.findAllByMarking(marking);

            if (!todayTime.equals(schedule.getIncome().toString())) {
                Delay delay = new Delay();
                delay.setMarking(marking);
                delayRepository.save(delay);

                delays.add(delay);
                marking.setDelays(delays);
            }

            res = new ApiResponse(HttpStatus.OK.value(), "Entry marking created", marking);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update Marking entity.
     *
     * @param markingId the markingId
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping("/profiles/{username}/markings/{marking_id}")
    public ResponseEntity<ApiResponse> updateSchedule(
            @PathVariable(value = "username") String username,
            @PathVariable(value = "marking_id") Long markingId)
            throws ResourceNotFoundException {
        ApiResponse res;

        try {
            Marking marking =
                    markingRepository
                            .findById(markingId)
                            .orElseThrow(() -> new ResourceNotFoundException("Marking not found on :: " + markingId));
            List<Delay> delays = delayRepository.findAllByMarking(marking);
            marking.setDepartureAt(new Date());
            Profile profile = marking.getProfile();
            Schedule schedule = profile.getSchedule();
            String todayTime = new SimpleDateFormat("HH:mm:ss").format(marking.getDepartureAt());

            if (!todayTime.equals(schedule.getIncome().toString())) {
                Delay delay = new Delay();
                delay.setMarking(marking);
                delayRepository.save(delay);
                delays.add(delay);
            }

            marking.setDelays(delays);

            res = new ApiResponse(HttpStatus.OK.value(), "Departure marking created", markingRepository.save(marking));
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete marking map.
     *
     * @param markingId the markingId
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/markings/{id}")
    public Map<String, Boolean> deleteMarking(@PathVariable(value = "id") Long markingId) throws Exception {
        Marking marking =
                markingRepository
                        .findById(markingId)
                        .orElseThrow(() -> new ResourceNotFoundException("Marking not found on :: " + markingId));

        markingRepository.delete(marking);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}