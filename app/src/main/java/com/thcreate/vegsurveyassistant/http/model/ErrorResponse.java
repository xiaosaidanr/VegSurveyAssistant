package com.thcreate.vegsurveyassistant.http.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @Expose
    @SerializedName("status_code")
    public int statusCode;

    @Expose
    @SerializedName("message")
    public String message;

//    @Expose
//    @SerializedName("errors")
//    public String errors;

}
