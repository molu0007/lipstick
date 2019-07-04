package com.qyy.app.lipstick.api;

import com.ibupush.molu.common.model.RespInfo;
import com.qyy.app.lipstick.Config;
import com.qyy.app.lipstick.model.request.BehaviorEnty;
import com.qyy.app.lipstick.model.response.login.LoginMoudle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-22 10:29
 * @name: BehaviorApiService
 */
public interface BehaviorApiService {
    @POST(Config.URL_HOME_ACTIVE)
    Call<RespInfo<Object>> uploadBehavior(@Body BehaviorEnty QyyAct );

    @GET(Config.URL_BEHAVIOR)
    Call<RespInfo<Object>> uploadBehaviorLog(@Query("t")String t,  @Query("platform")String platform,@Query("rseat")String rseat,@Query("item")String item,@Query("ext")String ext);
}
