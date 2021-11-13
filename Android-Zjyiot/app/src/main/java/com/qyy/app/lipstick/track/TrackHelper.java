package com.qyy.app.lipstick.track;

import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.CallDelegate;
import com.ibupush.molu.common.net.HttpManager;
import com.ibupush.molu.common.util.LogUtil;
import com.qyy.app.lipstick.NetResponseCall;
import com.qyy.app.lipstick.api.BehaviorApiService;
import com.qyy.app.lipstick.model.request.BehaviorEnty;

import retrofit2.Call;

public class TrackHelper implements CallDelegate {

   static TrackHelper trackHelper;
    private final BehaviorApiService mBehaviorApiService;

    public TrackHelper() {
        mBehaviorApiService=HttpManager.create(BehaviorApiService.class);
    }

    public static TrackHelper getTrackHelper() {
        if (trackHelper==null){
            trackHelper=new TrackHelper();
        }
        return trackHelper;
    }

    public void recordBehaver(String t,String rseat,String item,String ext) {

        BehaviorApiService behaviorApiService = HttpManager.create(BehaviorApiService.class);
        final Call<RespInfo<Object>> call = behaviorApiService.uploadBehaviorLog(t,"android",rseat,item,ext);
        call.enqueue(new NetResponseCall<Object>(this) {
            @Override
            protected void onSuccess(Call<RespInfo<Object>> call, Object data) {
                LogUtil.d(data.toString());
            }

            @Override
            protected void onFail(Call<RespInfo<Object>> call, int type, String code, String tip) {


            }
        });
    }

    @Override
    public boolean isHostDestroyed() {
        return true;
    }
}
