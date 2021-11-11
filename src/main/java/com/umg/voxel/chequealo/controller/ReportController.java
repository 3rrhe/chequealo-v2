package com.umg.voxel.chequealo.controller;

import com.umg.voxel.chequealo.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.umg.voxel.chequealo.utils.ApiResponse;
import org.springframework.web.bind.annotation.*;
import com.umg.voxel.chequealo.service.ReportsService;
import com.umg.voxel.chequealo.repository.UserRepository;
import com.umg.voxel.chequealo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.umg.voxel.chequealo.repository.DepartmentRepository;
import com.umg.voxel.chequealo.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class ReportController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ReportsService reportsService;

    /**
     * Get all marking list.
     *
     * @return the list
     */
    @GetMapping("/reports/markings")
    public ResponseEntity<ApiResponse> getAllDepartmentMarkingsReport() {
        ApiResponse res;

        try {
            res = new ApiResponse(HttpStatus.OK.value(), "All markings", reportsService.generateMarkingsReport("pdf"));
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all delays marking list.
     *
     * @return the list
     */
    @GetMapping("/reports/delays")
    public ResponseEntity<ApiResponse> getAllDelaysMarkingsReport() {
        ApiResponse res;

        try {
            res = new ApiResponse(HttpStatus.OK.value(), "All delays", reportsService.generateDelaysReport(Delay.TYPE_DELAY, "pdf"));
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all advances marking list.
     *
     * @return the list
     */
    @GetMapping("/reports/advances")
    public ResponseEntity<ApiResponse> getAllAdvancesMarkingsReport() {
        ApiResponse res;

        try {
            res = new ApiResponse(HttpStatus.OK.value(), "All advances", reportsService.generateDelaysReport(Delay.TYPE_ADVANCE, "pdf"));
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get employee marking list.
     *
     * @param id the profileId
     * @return the list
     */
    @GetMapping("/reports/department/{id}")
    public ResponseEntity<ApiResponse> getAllDepartmentMarkingsReport(@PathVariable(value = "id") Long id) {
        ApiResponse res;

        try {
            Department department =
                    departmentRepository
                            .findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Department not found on :: " + id));

            res = new ApiResponse(HttpStatus.OK.value(), "Department " + department.getName() + " markings", reportsService.generateDepartmentReport(department, "pdf"));
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
    @GetMapping("/reports/employee/{username}")
    public ResponseEntity<ApiResponse> getAllProfileMarkingsReport(@PathVariable(value = "username") String username) {
        ApiResponse res;

        try {
            Cuser cuser =
                    userRepository
                            .findByUsername(username)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + username));

            Employee employee = employeeRepository
                    .findByCuser(cuser)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found on user :: " + username));

            res = new ApiResponse(HttpStatus.OK.value(), "Employee " + username + " markings", reportsService.generateEmployeeReport(employee, "pdf"));
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
        } catch (Exception e) {
            res = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}