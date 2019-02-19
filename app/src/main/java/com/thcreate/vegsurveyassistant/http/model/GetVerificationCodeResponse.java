package com.thcreate.vegsurveyassistant.http.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVerificationCodeResponse {

    @Expose
    @SerializedName("key")
    public String verificationKey;

    @Expose
    @SerializedName("expired_at")
    public String expiredAt;

}
