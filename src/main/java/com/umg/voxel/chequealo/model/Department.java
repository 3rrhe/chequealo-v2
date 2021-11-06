package com.umg.voxel.chequealo.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "department")
@EntityListeners(AuditingEntityListener.class)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long departmentId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Transient
    private List<JobPosition> jobPositions;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return departmentId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.departmentId = id;
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
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the list of jobPositions
     */
    public List<JobPosition> getJobPositions() {
        return jobPositions;
    }

    /**
     * @param jobPositions the list of jobPositions
     */
    public void setJobPositions(List<JobPosition> jobPositions) {
        this.jobPositions = jobPositions;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + departmentId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}