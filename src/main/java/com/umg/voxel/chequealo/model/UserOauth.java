package com.umg.voxel.chequealo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "user_oauth")
@EntityListeners(AuditingEntityListener.class)
public class UserOauth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userOathId;

    @ManyToOne
    @JoinColumn(name = "cuser_id")
    private Cuser cuser;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @JsonIgnore
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return userOathId;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.userOathId = id;
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
    public void setUser(Cuser cuser) {
        this.cuser = cuser;
    }

    /**
     * Gets provider.
     *
     * @return the provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Sets provider.
     *
     * @param provider the provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets identifier.
     *
     * @param identifier the identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets accessToken.
     *
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets accessToken.
     *
     * @param accessToken the accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets refreshToken.
     *
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Sets refreshToken.
     *
     * @param refreshToken the refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "UserOauth{" +
                "id=" + userOathId +
                ", cuser='" + cuser.getId() + '\'' +
                ", provider='" + provider + '\'' +
                ", identifier='" + identifier + '\'' +
                ", access_token='" + accessToken + '\'' +
                ", refresh_token='" + refreshToken + '\'' +
                '}';
    }
}