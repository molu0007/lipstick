package com.qyy.app.lipstick.api;

import com.ibupush.molu.common.model.RespInfo;
import com.qyy.app.lipstick.Config;
import com.qyy.app.lipstick.model.response.order.OrderEntry;
import com.qyy.app.lipstick.model.response.order.RechareGoods;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * <p>类说明</p>
 *
 * @version V1.0
 * @author: dengwengen
 * @date: 2019-03-13 10:02
 * @name: MallApiService
 */
public interface MallApiService {

    @GET(Config.URL_MY_ORDER)
    Call<RespInfo<OrderEntry>> getOrderList();
    @GET(Config.URL_HOME_GAME_RECHARGE)
    Call<RespInfo<List<RechareGoods>>> getRechargeList();
    @GET(Config.URL_PAY_PREPAY)
    Call<RespInfo<String>> getPayParameter(@Query("rid")int rid,@Query("payType")String payType);
    @GET(Config.URL_PAY_RESULT)
    Call<RespInfo<Object>> getPayResult(@Query("orderId")String orderId);
}
