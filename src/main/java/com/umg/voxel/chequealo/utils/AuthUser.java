package com.umg.voxel.chequealo.utils;

/**
 * AuthUser class used to make login in API
 */
public class AuthUser {
    private String username;
    private String password;
    private String bearer;

    /**
     * @return string
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return string
     */
    public String getBearer() {
        return bearer;
    }

    /**
     * @param bearer the bearer token
     */
    public void setBearer(String bearer) {
        this.bearer = bearer;
    }
}
