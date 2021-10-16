package com.umg.rroca.wtorres.chequealo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "delay")
@EntityListeners(AuditingEntityListener.class)
public class Delay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long delayId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "marking_id")
    private Marking marking;

    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    @JsonIgnore
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
     * Gets marking.
     *
     * @return the marking
     */
    public Marking getMarking() {
        return marking;
    }

    /**
     * Sets marking.
     *
     * @param marking the marking
     */
    public void setMarking(Marking marking) {
        this.marking = marking;
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
                ", markingId='" + marking.getId() + '\'' +
                ", createdAt='" + createdAt.toString() + '\'' +
                ", deletedAt='" + deletedAt.toString() + '\'' +
                '}';
    }
}