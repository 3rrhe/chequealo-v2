package com.umg.rroca.wtorres.chequealo.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "profile")
@EntityListeners(AuditingEntityListener.class)
public class Profile {
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

    @Column(name = "job_position", nullable = false)
    private String jobPosition = JOB_OFFICE;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
    public String getJobPosition() {
        return jobPosition;
    }

    /**
     * Sets jobPosition.
     *
     * @param jobPosition the jobPosition
     */
    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    /**
     * Gets schedule.
     *
     * @return the schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Sets schedule.
     *
     * @param schedule the schedule
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUserId(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + profileId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", jobPosition='" + jobPosition + '\'' +
                ", scheduleId='" + schedule.getId() + '\'' +
                ", userId='" + user.getId() + '\'' +
                '}';
    }
}