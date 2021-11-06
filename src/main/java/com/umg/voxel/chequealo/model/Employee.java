package com.umg.voxel.chequealo.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "employee")
@EntityListeners(AuditingEntityListener.class)
public class Employee {
    private static final String JOB_OFFICE = "job_office";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long profileId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Cuser cuser;

    @ManyToOne
    @JoinColumn(name = "job_position_id")
    private JobPosition jobPosition;

    @Transient
    private List<Marking> markings;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return profileId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.profileId = id;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets jobPosition.
     *
     * @return the jobPosition
     */
    public JobPosition getJobPosition() {
        return jobPosition;
    }

    /**
     * Sets jobPosition.
     *
     * @param jobPosition the jobPosition
     */
    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public Cuser getUser() {
        return cuser;
    }

    /**
     * Sets user.
     *
     * @param cuser the user
     */
    public void setUserId(Cuser cuser) {
        this.cuser = cuser;
    }

    /**
     * @return the list of markings
     */
    public List<Marking> getMarkings() {
        return markings;
    }

    /**
     * @param markings the list of markings
     */
    public void setMarkings(List<Marking> markings) {
        this.markings = markings;
    }

    /**
     * Gets schedule.
     *
     * @return the schedule
     */
    public Schedule getSchedule() {
        return jobPosition.getSchedule();
    }

    /**
     * @param schedule the schedule
     */
    public void setSchedule(Schedule schedule) {
        this.jobPosition.setSchedule(schedule);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + profileId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", jobPosition='" + jobPosition.getId() + '\'' +
                ", userId='" + cuser.getId() + '\'' +
                '}';
    }
}