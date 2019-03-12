package com.thcreate.vegsurveyassistant.util;

public class HTTP {

    public static String SERVICE_URL = "http://39.96.195.235:88/";
    public static String TEST_URL = "https://www.baidu.com/";
    //请求超时时间4s
    public static int REQUEST_TIMEOUT = 4;

    //获取手机验证码的限制60000毫秒即60s
    public static int VERIFICATION_CODE_GET_TIME_LIMIT = 60000;
    //获取手机验证码请求所需的固定参数
    public static final String PHONE = "phone";
    //登录请求所需的固定参数
    public static final String PASSWORD = "password";

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String EXPIRES_IN = "expires_in";
    public static final String TOKEN_TYPE = "token_type";

    public static final String AUTHORIZATION = "Authorization";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static String client_id = "1";
    public static String client_secret = "eHCaErrRsJB1DyHWXQQL2LOgA7p5MsNBS8osqpxu";
    public static void changeClientId(String clientId){
        HTTP.client_id = clientId;
    }
    public static void changeClientSecret(String clientSecret){
        HTTP.client_secret = clientSecret;
    }

    //OSS related
    public static final String END_POINT = "";
    public static final String ACCESS_KEY_ID = "";
    public static final String ACCESS_KEY_SECRET = "";
    public static final String BUCKET_NAME = "";
    public static final String STS_SERVER = "";

}
