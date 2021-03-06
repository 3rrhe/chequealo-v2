package com.umg.voxel.chequealo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "cuser")
@EntityListeners(AuditingEntityListener.class)
public class Cuser {
    public static final String ROLE_CLIENT = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_REPORTER= "ROLE_REPORTER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cuserId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "role", nullable = false)
    private String role = ROLE_CLIENT;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt = new Date();

    @JsonIgnore
    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;

    @Transient
    private List<Employee> employees;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return cuserId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.cuserId = id;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets enabled.
     *
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
     * Gets createdAt.
     *
     * @return the createdAt
     */
    public Date getUpdateAt() {
        return updatedAt;
    }

    /**
     * Sets updatedAt.
     *
     * @param updatedAt the updatedAt
     */
    public void setUpdateAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
     * @return the list of profiles
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * @param employees the list of profiles
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Cuser{" +
                "id=" + cuserId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", enabled='" + enabled + '\'' +
                ", createdAt='" + createdAt.toString() + '\'' +
                ", updatedAt='" + updatedAt.toString() + '\'' +
                ", deletedAt='" + deletedAt.toString() + '\'' +
                '}';
    }
}