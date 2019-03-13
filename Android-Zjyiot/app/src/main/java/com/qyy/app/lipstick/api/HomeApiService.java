package com.qyy.app.lipstick.api;

import com.ibupush.molu.common.model.RespInfo;

import java.util.List;

import com.qyy.app.lipstick.Config;
import com.qyy.app.lipstick.model.response.CardInfo;
import com.qyy.app.lipstick.model.response.OrderDetail;
import com.qyy.app.lipstick.model.response.OrderRecord;
import com.qyy.app.lipstick.model.response.PackageInfo;
import com.qyy.app.lipstick.model.response.PackageList;
import com.qyy.app.lipstick.model.response.PayInfo;
import com.qyy.app.lipstick.model.response.Status;
import com.qyy.app.lipstick.model.response.home.GoodsList;
import com.qyy.app.lipstick.model.response.home.OverlendingInfo;
import com.qyy.app.lipstick.model.response.home.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author dengwg
 * @date 2018/3/15
 */
public interface HomeApiService {
    @GET(Config.URL_HOME)
    Call<RespInfo<CardInfo>> getCardDatial(@Query("iccid")String iccid, @Query("cardNo")String cardNo, @Query("accountId")String accountId);
    @GET(Config.URL_PACKAGE_LIST)
    Call<RespInfo<List<PackageList>>> getPackageList(@Query("iccid")String iccid, @Query("cardNo")String cardNo, @Query("accountId")String accountId);
    @GET(Config.URL_PACKAGE_INFO)
    Call<RespInfo<PackageInfo>> getPackageInfo(@Query("iccid")String iccid, @Query("cardNo")String cardNo,
                                               @Query("accountId")String accountId, @Query("productId")String productId);
    @POST(Config.URL_ADDORDER)
    Call<RespInfo<OrderDetail>> getOrderInfo(@Query("iccid")String iccid, @Query("cardNo")String cardNo,
                                             @Query("accountId")String accountId, @Query("productId")String
                                                   productId);

    @GET(Config.URL_ORDER_LIST)
    Call<RespInfo<OrderRecord>> getOrderList(@Query("iccid")String iccid, @Query("cardNo")String cardNo,
                                             @Query("accountId")String accountId, @Query("pageNo")int
                                                     pageNo, @Query("pageSize")int pageSize, @Query("date")String date);

    @GET(Config.URL_PAY_INFO)
    Call<RespInfo<PayInfo>> getPayInfo(@Query("iccid")String iccid, @Query("cardNo")String cardNo,
                                       @Query("accountId")String accountId, @Query("orderNo")String orderNo, @Query("payType")String payType);


    @GET(Config.URL_ACCOUNT_ID)
    Call<RespInfo<String>> getAccountId(@Query("iccid")String iccid);

    @GET(Config.URL_GETORDERSTATUS)
    Call<RespInfo<Status>> getOrderStatus(@Query("iccid")String iccid, @Query("cardNo")String cardNo, @Query("accountId")String accountId, @Query("orderNo")String orderNo);


    /************************口红机***************************/
    @GET(Config.URL_HOME_INDEX)
    Call<RespInfo<GoodsList>> getGoodsList();
    @GET(Config.URL_HOME_OVERLEND)
    Call<RespInfo<List<OverlendingInfo>> >getOverlendList();

    /**
     * 商品id
     * @param gid
     * @return
     */
    @GET(Config.URL_HOME_GAME)
    Call<RespInfo<Object>> initGame(@Query("gid")String gid);
    @GET(Config.URL_USER_INFO)
    Call<RespInfo<UserInfo>> getUserInfo();
    @GET(Config.URL_USER_ACTIVE)
    Call<RespInfo<Object>> getUserConfig();
}
