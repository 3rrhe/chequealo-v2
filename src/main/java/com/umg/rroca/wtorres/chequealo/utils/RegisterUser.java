package com.umg.rroca.wtorres.chequealo.utils;

/**
 * RegisterUser class used to register in API
 */
public class RegisterUser {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
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
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return string
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return string
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return string
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
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
