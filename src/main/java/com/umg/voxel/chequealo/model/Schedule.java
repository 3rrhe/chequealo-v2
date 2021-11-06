package com.umg.voxel.chequealo.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Time;
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
    private Time income;

    @Column(name = "lunch_start", nullable = false)
    private Time lunchStart;

    @Column(name = "lunch_end", nullable = false)
    private Time lunchEnd;

    @Column(name = "output", nullable = false)
    private Time output;

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
    public Time getIncome() {
        return income;
    }

    /**
     * Sets income.
     *
     * @param income the income
     */
    public void setIncome(Time income) {
        this.income = income;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public Time getLunchStart() {
        return lunchStart;
    }

    /**
     * Sets lunchStart.
     *
     * @param lunchStart the lunchStart
     */
    public void setLunchStart(Time lunchStart) {
        this.lunchStart = lunchStart;
    }

    /**
     * Gets lunchEnd.
     *
     * @return the lunchEnd
     */
    public Time getLunchEnd() {
        return lunchEnd;
    }

    /**
     * Sets lunchEnd.
     *
     * @param lunchEnd the lunchEnd
     */
    public void setLunchEnd(Time lunchEnd) {
        this.lunchEnd = lunchEnd;
    }

    /**
     * Gets output.
     *
     * @return the output
     */
    public Time getOutput() {
        return output;
    }

    /**
     * Sets output.
     *
     * @param output the output
     */
    public void setOutput(Time output) {
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