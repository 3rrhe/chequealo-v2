package com.umg.rroca.wtorres.chequealo.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {
    public static final String ROLE_CLIENT = "ROLE_USER";
    public static final String ROLE_BOSS = "ROLE_BOSS";
    public static final String ROLE_SECURITY = "ROLE_SECURITY";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "role", nullable = false)
    private String role = ROLE_CLIENT;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "apikey", nullable = false)
    private String apikey = RandomStringUtils.randomAlphanumeric(20).toUpperCase();

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt = new Date();

    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;

    @Transient
    private List<Profile> profiles;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return userId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.userId = id;
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
     * Gets apikey.
     *
     * @return the apikey
     */
    public String getApikey() {
        return apikey;
    }

    /**
     * Sets apikey.
     *
     * @param apikey the apikey
     */
    public void setApikey(String apikey) {
        this.apikey = apikey;
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
    public List<Profile> getProfiles() {
        return profiles;
    }

    /**
     * @param profiles the list of profiles
     */
    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", apikey='" + apikey + '\'' +
                ", role='" + role + '\'' +
                ", enabled='" + enabled + '\'' +
                ", createdAt='" + createdAt.toString() + '\'' +
                ", updatedAt='" + updatedAt.toString() + '\'' +
                ", deletedAt='" + deletedAt.toString() + '\'' +
                '}';
    }
}