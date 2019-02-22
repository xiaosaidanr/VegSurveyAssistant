package com.thcreate.vegsurveyassistant.http.interceptor;

import android.util.Log;

import com.thcreate.vegsurveyassistant.http.api.TokenApi;
import com.thcreate.vegsurveyassistant.http.model.Token;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.HTTP;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

public class TokenInterceptor implements Interceptor {

    private static final String TAG = "TokenInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        //为请求添加authorization head
        String accessToken = "Bearer " + SessionManager.getToken().accessToken;
        Request request = chain.request().newBuilder()
                .addHeader(HTTP.AUTHORIZATION, accessToken)
                .build();
        //执行请求
        Response response = chain.proceed(request);
        //根据response的code判断access_token是否过期
        if (isTokenExpired(response)){
            Log.e(TAG, "access_token expired!");
            //如果access_token过期 则尝试利用refresh_token去请求更新access_token
            retrofit2.Response<Token> refreshTokenResponse = refreshToken();
            //如果请求返回null 说明应用代码或者网络出错
            if (refreshTokenResponse != null){
                //根据服务器返回的code 判断refresh_token是否过期
                if (isTokenExpired(refreshTokenResponse)){
                    //TODO logout
                    Log.e(TAG, "refresh_token expired too!");
                }
                else {
                    //refresh_token未过期 并返回了新的token
                    if (refreshTokenResponse.body() != null){
                        Token newToken = refreshTokenResponse.body();
                        //更新token
                        SessionManager.setToken(newToken);
                        String newAccessToken = "Bearer " + newToken.accessToken;
                        Request newRequest = request.newBuilder()
                                .removeHeader(HTTP.AUTHORIZATION)
                                .addHeader(HTTP.AUTHORIZATION, newAccessToken)
                                .build();
                        response = chain.proceed(newRequest);
                    }
                }
            }
            else {
                Log.e(TAG, "refreshToken method error!");
            }
        }
        return response;
    }

    /**
     * 根据服务器返回的错误码判断token是否过期
     * @param response okhttp里面的response
     * @return true代表token过期 false代表token未过期
     */
    private boolean isTokenExpired(Response response){
        //access_token或者refresh_token过期服务器会返回401
        if (response.code() == 401){
            return true;
        }
        return false;
    }

    /**
     * 根据服务器返回的错误码判断token是否过期
     * @param response retrofit2里面的response
     * @return true代表token过期 false代表token未过期
     */
    private boolean isTokenExpired(retrofit2.Response response){
        return isTokenExpired(response.raw());
    }

    /**
     * 同步刷新token
     * @return 同步刷新token的结果response null代表软件报错
     */
    private retrofit2.Response<Token> refreshToken(){
        retrofit2.Response<Token> response = null;
        TokenApi tokenApi = HttpServiceGenerator.getInstance().createService(TokenApi.class);
        Token oldToken = SessionManager.getToken();
        String accessToken = "Bearer " + oldToken.accessToken;
        String refreshToken = oldToken.refreshToken;
        Call<Token> call = tokenApi.refreshTokenSync(accessToken, HTTP.REFRESH_TOKEN, HTTP.client_id, HTTP.client_secret, refreshToken);
        try {
            response = call.execute();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

}
