package com.accumulation.lee.androidaccumulationproject.demo;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * AndroidAccumulationProject
 * com.accumulation.lee.utils.retrofit.demo
 * Created by lee on 15/8/18.
 * Email:lee131483@gmail.com
 */
public interface LoginService {

    public static final String BASE_URL = "https://api.yourdomain.com";

    @POST("/login")
    User basicLogin();

    @POST("/token")
    AccessToken getAccessToken(@Query("code") String code,
                               @Query("grant_type") String grantType);

    static class User {
        public String name;
        public String password;
    }

    static class AccessToken {

        private String accessToken;
        private String tokenType;

        public String getAccessToken() {
            return accessToken;
        }

        public String getTokenType() {
            // OAuth requires uppercase Authorization HTTP header value for token type
            if (!Character.isUpperCase(tokenType.charAt(0))) {
                tokenType = Character.toString(tokenType.charAt(0)).toUpperCase() + tokenType.substring(1);
            }
            return tokenType;
        }
    }
}
