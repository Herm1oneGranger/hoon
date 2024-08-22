package com.bosch.framework.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Yuxin
 * @date 2022/8/19
 */
@Component
@ConfigurationProperties(prefix = "adfs")
public class AdfsConfig {
    private String tokenEndpoint;
    private String clientId;
    private String redirectUri;
    private String userInfoUrl;
    private String clientSecret;
    private String scope;
    private String grantTypeAuthorizationCode;
    private String codeVerifier;
    private String grantTypeRefreshToken;

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrantTypeAuthorizationCode() {
        return grantTypeAuthorizationCode;
    }

    public void setGrantTypeAuthorizationCode(String grantTypeAuthorizationCode) {
        this.grantTypeAuthorizationCode = grantTypeAuthorizationCode;
    }

    public String getCodeVerifier() {
        return codeVerifier;
    }

    public void setCodeVerifier(String codeVerifier) {
        this.codeVerifier = codeVerifier;
    }

    public String getGrantTypeRefreshToken() {
        return grantTypeRefreshToken;
    }

    public void setGrantTypeRefreshToken(String grantTypeRefreshToken) {
        this.grantTypeRefreshToken = grantTypeRefreshToken;
    }

    @Override
    public String toString() {
        return "AdfsConfiguration{" +
                "token_endpoint='" + tokenEndpoint + '\'' +
                ", clientId='" + clientId + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", scope='" + scope + '\'' +
                ", grantTypeAuthorizationCode='" + grantTypeAuthorizationCode + '\'' +
                ", codeVerifier='" + codeVerifier + '\'' +
                ", grantTypeRefreshToken='" + grantTypeRefreshToken + '\'' +
                '}';
    }
}
