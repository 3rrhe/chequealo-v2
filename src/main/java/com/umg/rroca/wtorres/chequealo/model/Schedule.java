package com.umg.rroca.wtorres.chequealo.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "schedule")
@EntityListeners(AuditingEntityListener.class)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long scheduleId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "income", nullable = false)
    private Date income;

    @Column(name = "lunch_start", nullable = false)
    private Date lunchStart;

    @Column(name = "lunch_end", nullable = false)
    private Date lunchEnd;

    @Column(name = "output", nullable = false)
    private Date output;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return scheduleId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.scheduleId = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets income.
     *
     * @return the income
     */
    public Date getIncome() {
        return income;
    }

    /**
     * Sets income.
     *
     * @param income the income
     */
    public void setIncome(Date income) {
        this.income = income;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public Date getLunchStart() {
        return lunchStart;
    }

    /**
     * Sets lunchStart.
     *
     * @param lunchStart the lunchStart
     */
    public void setLunchStart(Date lunchStart) {
        this.lunchStart = lunchStart;
    }

    /**
     * Gets lunchEnd.
     *
     * @return the lunchEnd
     */
    public Date getLunchEnd() {
        return lunchEnd;
    }

    /**
     * Sets lunchEnd.
     *
     * @param lunchEnd the lunchEnd
     */
    public void setLunchEnd(Date lunchEnd) {
        this.lunchEnd = lunchEnd;
    }

    /**
     * Gets output.
     *
     * @return the output
     */
    public Date getOutput() {
        return output;
    }

    /**
     * Sets output.
     *
     * @param output the output
     */
    public void setOutput(Date output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + scheduleId +
                ", name='" + name + '\'' +
                ", income='" + income.toString() + '\'' +
                ", lunchStart='" + lunchStart.toString() + '\'' +
                ", lunchEnd='" + lunchEnd.toString() + '\'' +
                ", output='" + output.toString() + '\'' +
                '}';
    }
}