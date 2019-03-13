package com.ibupush.molu.common.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Http api service
 *
 * @author Shyky
 * @date 2017/9/8
 */
interface ApiService {
    @GET
    Call<ResponseBody> downloadFile(@Url String url);
}