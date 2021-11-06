package com.umg.voxel.chequealo.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public class ConfigProperty {
    private String jwtSecret;
    private int jwtExpiration;
    private String pwdSeed;

    /**
     * @return string
     */
    public String getJwtSecret() {
        return jwtSecret;
    }

    /**
     * @param jwtSecret the jwt secret key
     */
    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    /**
     * @return int
     */
    public int getJwtExpiration() {
        return jwtExpiration;
    }

    /**
     * @param jwtExpiration the expiration time
     */
    public void setJwtExpiration(int jwtExpiration) {
        this.jwtExpiration = jwtExpiration;
    }

    /**
     * @return string
     */
    public String getSeed() {
        return pwdSeed;
    }

    /**
     * @param seed the seed
     */
    public void setSeed(String seed) {
        this.pwdSeed = seed;
    }
}
