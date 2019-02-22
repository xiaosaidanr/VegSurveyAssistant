package com.thcreate.vegsurveyassistant.http.api;

import com.thcreate.vegsurveyassistant.http.model.Token;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface TokenApi {

    /**
     * 可同步的刷新token的方法
     * @param accessToken access_token
     * @param grantType 固定填refresh_token
     * @param clientId app的client_id
     * @param clientSecret app的client_secret
     * @param refreshToken refresh_token
     * @return
     */
    @FormUrlEncoded
    @PUT("api/authorizations/current")
    Call<Token> refreshTokenSync(@Header("Authorization") String accessToken,
                                 @Field("grant_type") String grantType,
                                 @Field("client_id") String clientId,
                                 @Field("client_secret") String clientSecret,
                                 @Field("refresh_token") String refreshToken);

    /**
     * 非同步的刷新token的方法
     * @param accessToken access_token
     * @param grantType 固定填refresh_token
     * @param clientId app的client_id
     * @param clientSecret app的client_secret
     * @param refreshToken refresh_token
     * @return
     */
    @FormUrlEncoded
    @PUT("api/authorizations/current")
    Observable<Token> refreshTokenAsync(@Header("Authorization") String accessToken,
                                        @Field("grant_type") String grantType,
                                        @Field("client_id") String clientId,
                                        @Field("client_secret") String clientSecret,
                                        @Field("refresh_token") String refreshToken);

}
