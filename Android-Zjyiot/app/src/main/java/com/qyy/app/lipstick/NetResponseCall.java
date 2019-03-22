package com.qyy.app.lipstick;

import android.content.Intent;

import com.ibupush.molu.common.model.RespInfo;
import com.ibupush.molu.common.net.CallDelegate;
import com.ibupush.molu.common.net.LoggerIntercepter;
import com.ibupush.molu.common.net.NetConstans;
import com.ibupush.molu.common.util.LogUtil;

import com.qyy.app.lipstick.ui.activity.login.LoginActivity;
import com.qyy.app.lipstick.utils.ResourceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ibupush.molu.common.net.NetConstans.CODE_EXCEPTION;
import static com.ibupush.molu.common.net.NetConstans.STATUS_SUCCESS;

/**
 * @author dengwg
 * @date 2018/3/15
 *@param <T> 数据实体bean，每个接口传入具体类型
 */
public abstract class NetResponseCall<T> implements Callback<RespInfo<T>> {
    public static final String TAG = "MyCallBack";
    /**
     * 请求的分发
     */
    private CallDelegate mDelegate;

    /**
     * 构造一个请求回调,带取消
     *
     * @param delegate 调用请求的activity或fragment,当销毁时销毁时取消回调
     */
    public NetResponseCall(CallDelegate delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public void onResponse(Call<RespInfo<T>> call, Response<RespInfo<T>> response) {
        LogUtil.d("onResponse -> " + response);
        //如果delegate不为空,判断状态,如果宿主页面被销毁,取消回调
        if (mDelegate != null && mDelegate.isHostDestroyed()) {
            LogUtil.d(TAG, "请求的activity或fragment被销毁,取消回调");
            return;
        }
        if (response.isSuccessful()) {
            //网络请求成功
            RespInfo<T> respInfo = response.body();
            if ( STATUS_SUCCESS.equals(respInfo.errno)) {
                //接口调用成功
                //处理后台返回数据
                onSuccess(call, respInfo);
            } else {
                //接口调用失败
                Intent intent;
                if (NetConstans.CODE_NEED_LOGIN.equals(respInfo.errno)) {
                    //token失效or当前账号已在其他终端登陆-->调到登录页
                    intent = new Intent(BaseApplication.getAppContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    BaseApplication.getAppContext().startActivity(intent);
                    return;
                }
                //处理后台错误码
                onFail(call, NetConstans.ERROR_CODE_SERVER, respInfo.errno, respInfo.errmsg);
            }
        } else {
            //网络请求错误(http响应码非2xx成功类)
            String excpMsg = response.message() != null ? response.message() : "网络请求错误";
            LogUtil.e(TAG, "请求失败:=" + excpMsg);
            onFail(call, NetConstans.ERROR_CODE_HTTP, response.code() + "", ResourceUtil.getString(R.string.toast_net_exception));
        }
        onFinish();
    }

    /**
     * 接口调用成功
     *
     * @param call     请求对象
     * @param respInfo 接口完整响应体
     */
    protected void onSuccess(Call<RespInfo<T>> call, RespInfo<T> respInfo) {
        if (respInfo.data!=null)
        onSuccess(call, respInfo.data);
    }

    @Override
    public void onFailure(Call<RespInfo<T>> call, Throwable t) {
        LogUtil.e(LoggerIntercepter.TAG, "onFailure -> " + t.toString());
        //如果delegate不为空,判断状态,如果宿主页面被销毁,取消回调
        if (mDelegate != null && mDelegate.isHostDestroyed()) {
            LogUtil.d(TAG, "请求的activity或fragment被销毁,取消回调");
            return;
        }
        //异常信息返回统一的错误信息
        onFail(call, NetConstans.ERROR_EXCEPTION, CODE_EXCEPTION, ResourceUtil.getString(R.string.toast_net_exception));
        onFinish();
    }

    /**
     * 后台返回成功码(100)
     *
     * @param call 请求对象
     * @param data 响应数据
     */
    protected abstract void onSuccess(Call<RespInfo<T>> call, T data);

    /**
     * 网络请求失败
     *
     * @param call 请求对象
     * @param type 失败类型
     *             <p>
     *             ERROR_CODE_SERVER:后台返回错误码(status = 0)<br>
     *             ERROR_CODE_HTTP:网络请求错误(http响应码非2xx成功类)<br>
     *             ERROR_EXCEPTION:网络请求异常<br>
     *             </p>
     * @param code 错误码
     *             <p>
     *             后台返回错误码:响应code字段<br>
     *             网络请求错误:http状态码<br>
     *             网络异常:-1<br>
     *             </p>
     * @param tip  错误详情
     *             <p>
     *             后台返回错误码:响应tip字段<br>
     *             网络请求错误:http状态消息<br>
     *             网络异常:异常详情<br>
     *             </p>
     */
    protected abstract void onFail(Call<RespInfo<T>> call, int type, String code, String tip);

    /**
     * 当网络请求执行完成
     * {@link #onSuccess(Call, Object)}和{@link #onFail(Call, int, String, String)}之后都会回调此方法
     */
    protected void onFinish() {

    }
}