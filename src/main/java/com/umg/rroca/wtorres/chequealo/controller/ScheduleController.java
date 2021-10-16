package com.umg.rroca.wtorres.chequealo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.umg.rroca.wtorres.chequealo.model.Schedule;
import com.umg.rroca.wtorres.chequealo.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.umg.rroca.wtorres.chequealo.repository.ScheduleRepository;
import com.umg.rroca.wtorres.chequealo.exception.ResourceNotFoundException;

import java.util.Map;
import java.util.HashMap;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    /**
     * Get all schedule list.
     *
     * @return the list
     */
    @GetMapping("/schedules")
    public ResponseEntity<ApiResponse> getAllSchedules() {
        ApiResponse res;

        try {
            res = new ApiResponse(HttpStatus.OK.value(), "Schedules list", scheduleRepository.findAll());
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets schedules by id.
     *
     * @param scheduleId the scheduleId
     * @return the schedule by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/schedules/{id}")
    public ResponseEntity<ApiResponse> getSchedulesById(@PathVariable(value = "id") Long scheduleId)
            throws ResourceNotFoundException {
        ApiResponse res;

        try {
            Schedule schedule =
                    scheduleRepository
                            .findById(scheduleId)
                            .orElseThrow(() -> new ResourceNotFoundException("Schedule not found on :: " + scheduleId));

            res = new ApiResponse(HttpStatus.OK.value(), "Schedule details", schedule);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create new schedule.
     *
     * @param schedule the schedule
     * @return the schedule
     */
    @PostMapping("/schedules")
    public ResponseEntity<ApiResponse> createSchedule(@Valid @RequestBody Schedule schedule) {
        ApiResponse res;

        try {
            res = new ApiResponse(HttpStatus.OK.value(), "Schedule details", scheduleRepository.save(schedule));
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update schedule entity.
     *
     * @param scheduleId      the scheduleId
     * @param scheduleDetails the schedule details
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping("/schedules/{id}")
    public ResponseEntity<ApiResponse> updateSchedule(
            @PathVariable(value = "id") Long scheduleId, @Valid @RequestBody Schedule scheduleDetails)
            throws ResourceNotFoundException {
        ApiResponse res;

        try {
            Schedule schedule =
                    scheduleRepository
                            .findById(scheduleId)
                            .orElseThrow(() -> new ResourceNotFoundException("Schedule not found on :: " + scheduleId));

            schedule.setName(scheduleDetails.getName());
            schedule.setIncome(scheduleDetails.getIncome());
            schedule.setLunchStart(scheduleDetails.getLunchStart());
            schedule.setLunchEnd(scheduleDetails.getLunchEnd());
            schedule.setOutput(scheduleDetails.getOutput());

            final Schedule updatedSchedule = scheduleRepository.save(schedule);

            res = new ApiResponse(HttpStatus.OK.value(), "Updated schedule", updatedSchedule);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete schedule map.
     *
     * @param scheduleId the scheduleId
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/schedules/{id}")
    public Map<String, Boolean> deleteSchedule(@PathVariable(value = "id") Long scheduleId) throws Exception {
        Schedule schedule =
                scheduleRepository
                        .findById(scheduleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Schedule not found on :: " + scheduleId));

        scheduleRepository.delete(schedule);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}