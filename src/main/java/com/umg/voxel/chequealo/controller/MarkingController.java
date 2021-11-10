package com.umg.voxel.chequealo.controller;

import com.umg.voxel.chequealo.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.umg.voxel.chequealo.utils.ApiResponse;
import com.umg.voxel.chequealo.repository.UserRepository;
import com.umg.voxel.chequealo.repository.DelayRepository;
import com.umg.voxel.chequealo.repository.MarkingRepository;
import com.umg.voxel.chequealo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.umg.voxel.chequealo.exception.ResourceNotFoundException;

import java.util.*;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api/v1")
public class MarkingController {

    @Autowired
    private MarkingRepository markingRepository;

    @Autowired
    private DelayRepository delayRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

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
     * Get all employee marking list.
     *
     * @param username the profileId
     * @return the list
     */
    @GetMapping("/employees/{username}/markings")
    public ResponseEntity<ApiResponse> getAllProfileMarkings(@PathVariable(value = "username") String username) {
        ApiResponse res;

        try {
            Cuser cuser =
                    userRepository
                            .findByUsername(username)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + username));

            Employee employee = employeeRepository
                    .findByCuser(cuser)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + username));

            List<Marking> markings = markingRepository.findAllByEmployee(employee);

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
     * @param username the username
     * @return the schedule
     */
    @PostMapping("/employees/{username}/markings")
    public ResponseEntity<ApiResponse> createSchedule(@PathVariable(value = "username") String username) {
        ApiResponse res;

        try {
            Marking marking;
            Cuser cuser =
                    userRepository
                            .findByUsername(username)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + username));

            Employee employee = employeeRepository
                    .findByCuser(cuser)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + username));

            Schedule schedule = employee.getSchedule();

            List<Marking> markings = markingRepository
                    .findAllByEmployee(employee);

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

            marking.setEmployee(employee);
            marking.setEntryAt(new Date());
            Date today = new Date();

            markingRepository.save(marking);
            List<Delay> delays = delayRepository.findAllByMarking(marking);

            if (!today.equals(schedule.getIncome())) {
                Delay delay = new Delay();
                delay.setMarking(marking);

                if (!today.before(schedule.getIncome())) {
                    delay.setType(Delay.TYPE_ADVANCE);
                }

                if (!today.after(schedule.getIncome())) {
                    delay.setType(Delay.TYPE_DELAY);
                }

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
    @PutMapping("/employees/{username}/markings/{marking_id}")
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
            Employee employee = marking.getEmployee();
            Schedule schedule = employee.getSchedule();

            if (!marking.getDepartureAt().equals(schedule.getOutput())) {
                Delay delay = new Delay();
                delay.setMarking(marking);

                if (marking.getDepartureAt().before(schedule.getOutput())) {
                    delay.setType(Delay.TYPE_ADVANCE);
                }

                if (marking.getDepartureAt().after(schedule.getOutput())) {
                    delay.setType(Delay.TYPE_DELAY);
                }

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