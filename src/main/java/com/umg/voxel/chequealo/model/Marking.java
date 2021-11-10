package com.umg.voxel.chequealo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "marking")
@EntityListeners(AuditingEntityListener.class)
public class Marking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long markingId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "entry_at", nullable = false)
    private Date entryAt;

    @Column(name = "departure_at", nullable = true)
    private Date departureAt;

    @JsonIgnore
    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;

    @Transient
    private List<Delay> delays;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return markingId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.markingId = id;
    }

    /**
     * Gets employee.
     *
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets employee.
     *
     * @param employee the employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Gets entryAt.
     *
     * @return the entryAt
     */
    public Date getEntryAt() {
        return entryAt;
    }

    /**
     * Sets entryAt.
     *
     * @param entryAt the entryAt
     */
    public void setEntryAt(Date entryAt) {
        this.entryAt = entryAt;
    }

    /**
     * Gets departureAt.
     *
     * @return the departureAt
     */
    public Date getDepartureAt() {
        return departureAt;
    }

    /**
     * Sets departureAt.
     *
     * @param departureAt the departure_at
     */
    public void setDepartureAt(Date departureAt) {
        this.departureAt = departureAt;
    }

    /**
     * Gets deletedAt.
     *
     * @return the deletedAt
     */
    public Date getDeletedAt() {
        return deletedAt;
    }

    /**
     * Sets deletedAt.
     *
     * @param deletedAt the deletedAt
     */
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * @return the list of delays
     */
    public List<Delay> getDelays() {
        return delays;
    }

    /**
     * @param delays the list of delays
     */
    public void setDelays(List<Delay> delays) {
        this.delays = delays;
    }

    @Override
    public String toString() {
        return "Marking{" +
                "id=" + markingId +
                ", employeeId='" + employee.getId() + '\'' +
                ", entryAt='" + entryAt.toString() + '\'' +
                ", departureAt='" + departureAt.toString() + '\'' +
                ", deletedAt='" + deletedAt.toString() + '\'' +
                '}';
    }
}