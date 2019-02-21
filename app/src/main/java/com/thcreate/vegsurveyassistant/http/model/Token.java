package com.thcreate.vegsurveyassistant.http.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @Expose
    @SerializedName("token_type")
    public String tokenType;

    @Expose
    @SerializedName("expires_in")
    public int expiresIn;

    @Expose
    @SerializedName("access_token")
    public String accessToken;

    @Expose
    @SerializedName("refresh_token")
    public String refreshToken;

}
