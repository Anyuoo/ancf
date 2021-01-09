package com.anyu.authservice.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "app.jwt", ignoreInvalidFields = true)
@Component
public class JwtProperties {
    //秘钥
    private String secretKey = "7eb374edfac7cc268f7dab26c8595579576959ba53ca50d2c11a324e";
    //jwt接收对象
    private String aud;
    //jwt的签发主体
    private String iss;
    //过期时间
    @DurationUnit(ChronoUnit.HOURS)
    private Duration expiredHours = Duration.ofHours(24);
    private String authHeaderKey = "Authorization";
    private String tokenPrefix = "Bearer ";


    public String getSecretKey() {
        return secretKey;
    }

    public JwtProperties setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public String getAud() {
        return aud;
    }

    public JwtProperties setAud(String aud) {
        this.aud = aud;
        return this;
    }

    public String getIss() {
        return iss;
    }

    public JwtProperties setIss(String iss) {
        this.iss = iss;
        return this;
    }

    public Duration getExpiredHours() {
        return expiredHours;
    }

    public JwtProperties setExpiredHours(Duration expiredHours) {
        this.expiredHours = expiredHours;
        return this;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public JwtProperties setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
        return this;
    }

    public String getAuthHeaderKey() {
        return authHeaderKey;
    }

    public JwtProperties setAuthHeaderKey(String authHeaderKey) {
        this.authHeaderKey = authHeaderKey;
        return this;
    }
}
