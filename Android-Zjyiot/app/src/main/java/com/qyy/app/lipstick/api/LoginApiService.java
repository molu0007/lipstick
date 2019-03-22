package com.qyy.app.lipstick.api;

import com.ibupush.molu.common.model.RespInfo;
import com.qyy.app.lipstick.Config;
import com.qyy.app.lipstick.model.response.CardInfo;
import com.qyy.app.lipstick.model.response.login.LoginMoudle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-09 17:04
 * @name: LoginApiService
 */
public interface LoginApiService {

    @GET(Config.URL_SMS_CODE)
    Call<RespInfo<String>> getSmsCode(@Query("phone")String phone);
    @POST(Config.URL_LOGIN)
    Call<RespInfo<LoginMoudle>> login(@Query("mobile")String mobile, @Query("code")String code);

    @POST(Config.URL_USER_WX_LOGIN)
    Call<RespInfo<LoginMoudle>> loginWX( @Query("code")String code);
}
