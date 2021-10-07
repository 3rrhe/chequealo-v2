package com.umg.rroca.wtorres.chequealo.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "delay")
@EntityListeners(AuditingEntityListener.class)
public class Delay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long delayId;

    @Column(name = "marking_id", nullable = false)
    private long markingId;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return delayId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.delayId = id;
    }

    /**
     * Gets markingId.
     *
     * @return the markingId
     */
    public long getMarkingId() {
        return markingId;
    }

    /**
     * Sets markingId.
     *
     * @param markingId the markingId
     */
    public void setMarkingId(long markingId) {
        this.markingId = markingId;
    }

    /**
     * Gets createdAt.
     *
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets createdAt.
     *
     * @param createdAt the createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        return "Delay{" +
                "id=" + delayId +
                ", markingId='" + markingId + '\'' +
                ", createdAt='" + createdAt.toString() + '\'' +
                ", deletedAt='" + deletedAt.toString() + '\'' +
                '}';
    }
}