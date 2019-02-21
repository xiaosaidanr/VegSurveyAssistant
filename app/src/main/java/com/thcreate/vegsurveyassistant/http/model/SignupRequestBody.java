package com.thcreate.vegsurveyassistant.http.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SignupRequestBody {

    @Expose
    @SerializedName("verification_key")
    public String verificationKey;

    @Expose
    @SerializedName("verification_code")
    public String verificationCode;

    @Expose
    @SerializedName("name")
    public String name;

    @Expose
    @SerializedName("password")
    public String password;

    @Expose
    @SerializedName("phone")
    public String phone;

    @Expose
    @SerializedName("type")
    public String type;

    private Map<String, String> requestDataMap;

    public SignupRequestBody(String verificationKey, String verificationCode, String name, String password, String phone, String type) {
        this.verificationKey = verificationKey;
        this.verificationCode = verificationCode;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.type = type;
        requestDataMap = new HashMap<>();
        requestDataMap.put("verification_key", verificationKey);
        requestDataMap.put("verification_code", verificationCode);
        requestDataMap.put("name", name);
        requestDataMap.put("password", password);
        requestDataMap.put("phone", phone);
        requestDataMap.put("type", type);
    }

    public Map<String, RequestBody> generateRequestBody() {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

}
