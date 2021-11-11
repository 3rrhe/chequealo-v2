package com.umg.voxel.chequealo.utils;

import java.sql.Timestamp;

public class DelayEmployeeReport {
    private Long cuserId;
    private String fullName;
    private String username;
    private String department;
    private String jobPosition;
    private Integer quantity;

    /**
     * @return cuserId
     */
    public Long getCuserId() {
        return cuserId;
    }

    /**
     * @param cuserId the cuserId
     */
    public void setCuserId(Long cuserId) {
        this.cuserId = cuserId;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return jobPosition
     */
    public String getJobPosition() {
        return jobPosition;
    }

    /**
     * @param jobPosition the jobPosition
     */
    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    /**
     * @return fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
