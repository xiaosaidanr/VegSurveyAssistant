package com.thcreate.vegsurveyassistant.http.api;

import com.thcreate.vegsurveyassistant.http.model.GetVerificationCodeResponse;
import com.thcreate.vegsurveyassistant.http.model.Token;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;

public interface AuthApi {

    /**
     * 获取手机验证码
     * @param type 类型 固定填 phone
     * @param phone 手机号
     * @return
     */
    @FormUrlEncoded
    @POST("api/verificationCodes")
    Call<GetVerificationCodeResponse> getVerificationCode(@Field("type") String type, @Field("phone") String phone);

    /**
     * 注册
     * @param requestBodyMap 注册时所需的参数
     *                       {
     *                          "verification_key": "xxx",//验证码key
     *                          "verification_code": "xxx"//验证码
     *                          "name": "xxx"//用户名
     *                          "password": "xxxxxx"//密码 不少于6个
     *                          "phone": "xxx"//手机号
     *                          "type": "phone"//固定填phone
     *                       }
     * @return
     */
    @Multipart
    @POST("api/users")
    Call<Token> signup(@PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * 登录
     * @param username 手机号
     * @param password 密码
     * @param clientId app的client_id
     * @param clientSecret app的client_secret
     * @param grantType 固定填password
     * @return
     */
    @FormUrlEncoded
    @POST("api/authorizations")
    Call<Token> login(@Field("username") String username,
                      @Field("password") String password,
                      @Field("client_id") String clientId,
                      @Field("client_secret") String clientSecret,
                      @Field("grant_type") String grantType);

    /**
     * 刷新token
     * @param accessToken access_token
     * @param grantType 固定填refresh_token
     * @param clientId app的client_id
     * @param clientSecret app的client_secret
     * @param refreshToken refresh_token
     * @return
     */
    @FormUrlEncoded
    @PUT("api/authorizations/current")
    Call<Token> refreshToken(@Header("Authorization") String accessToken,
                             @Field("grant_type") String grantType,
                             @Field("client_id") String clientId,
                             @Field("client_secret") String clientSecret,
                             @Field("refresh_token") String refreshToken);

}
