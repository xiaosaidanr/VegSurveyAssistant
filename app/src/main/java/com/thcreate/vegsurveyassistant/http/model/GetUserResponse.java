package com.thcreate.vegsurveyassistant.http.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserResponse {

    @Expose
    @SerializedName("id")
    public int id;

    @Expose
    @SerializedName("name")
    public String name;

    @Expose
    @SerializedName("email")
    public String email;

    @Expose
    @SerializedName("avatar")
    public String avatar;

    @Expose
    @SerializedName("bound_phone")
    public boolean boundPhone;

    @Expose
    @SerializedName("bound_wechat")
    public boolean boundWechat;

    @Expose
    @SerializedName("created_at")
    public String createdAt;

    @Expose
    @SerializedName("updated_at")
    public String updatedAt;

}
