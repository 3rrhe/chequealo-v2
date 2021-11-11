package com.umg.voxel.chequealo.utils;

import java.sql.Timestamp;

public class EmployeeMarkingReport {
    private Long markingId;
    private Timestamp entryAt;
    private Timestamp departureAt;
    private Timestamp advanceAt;
    private Timestamp delayAt;

    /**
     * @return markingId
     */
    public Long getMarkingId() {
        return markingId;
    }

    /**
     * @param markingId the markingId
     */
    public void setMarkingId(Long markingId) {
        this.markingId = markingId;
    }

    /**
     * @return entryAt
     */
    public Timestamp getEntryAt() {
        return entryAt;
    }

    /**
     * @param entryAt the entryAt
     */
    public void setEntryAt(Timestamp entryAt) {
        this.entryAt = entryAt;
    }

    /**
     * @return departureAt
     */
    public Timestamp getDepartureAt() {
        return departureAt;
    }

    /**
     * @param departureAt the departureAt
     */
    public void setDepartureAt(Timestamp departureAt) {
        this.departureAt = departureAt;
    }

    /**
     * @return advanceAt
     */
    public Timestamp getAdvanceAt() {
        return advanceAt;
    }

    /**
     * @param advanceAt the advanceAt
     */
    public void setAdvanceAt(Timestamp advanceAt) {
        this.advanceAt = advanceAt;
    }

    /**
     * @return delayAt
     */
    public Timestamp getDelayAt() {
        return delayAt;
    }

    /**
     * @param delayAt the delayAt
     */
    public void setDelayAt(Timestamp delayAt) {
        this.delayAt = delayAt;
    }
}
