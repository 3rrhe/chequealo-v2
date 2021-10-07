package com.umg.rroca.wtorres.chequealo.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "marking")
@EntityListeners(AuditingEntityListener.class)
public class Marking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long markingId;

    @Column(name = "profile_id", nullable = false)
    private long profileId;

    @Column(name = "entry_at", nullable = false)
    private Date entryAt;

    @Column(name = "departure_at", nullable = true)
    private Date departureAt;

    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;

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
     * Gets profileId.
     *
     * @return the profileId
     */
    public long getProfileId() {
        return profileId;
    }

    /**
     * Sets profileId.
     *
     * @param profileId the profileId
     */
    public void setProfileId(long profileId) {
        this.profileId = profileId;
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

    @Override
    public String toString() {
        return "Marking{" +
                "id=" + markingId +
                ", profileId='" + profileId + '\'' +
                ", entryAt='" + entryAt.toString() + '\'' +
                ", departureAt='" + departureAt.toString() + '\'' +
                ", deletedAt='" + deletedAt.toString() + '\'' +
                '}';
    }
}