package com.qyy.app.lipstick.intercept;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.ibupush.molu.common.util.CollectionUtil;
import com.ibupush.molu.common.util.DensityUtil;
import com.ibupush.molu.common.util.DeviceUtil;
import com.ibupush.molu.common.util.JsonUtil;
import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.MD5Util;
import com.ibupush.molu.common.util.NetworkUtil;
import com.ibupush.molu.common.util.TextUtil;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qyy.app.lipstick.BuildConfig;
import com.qyy.app.lipstick.utils.PrefsUtil;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
/**
 * @author dengwg
 * @date 2018/3/15
 */

public final class HeadersInterceptor implements Interceptor {
    public static final String TAG = "HeadersInterceptor";
    private static final int MEDIA_TYPE_TEXT = 1;
    private static final int MEDIA_TYPE_FORM = 2;
    private static final int MEDIA_TYPE_JSON = 3;
    private static final int MEDIA_TYPE_XML = 4;
    private static final int MEDIA_TYPE_HTML = 5;
    private static final int MEDIA_TYPE_WEB_VIEW_HTML = 6;
    private Context context;

    public HeadersInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String versionName = BuildConfig.VERSION_NAME;
        final String appType = "iot.zjy.android";
        final String currentTime = System.currentTimeMillis() + "";
//        String token = PrefsUtil.getToken(context);
        final String salt = "sdde4547hk8xtH5kF4j2BAZaBMHjKT12IjxIjP0IJRP2Dje4" +
                "axd1K7SYgF96bIimBUYS8mi8m9Smf3GS1LSuIjn3QEfRbU";
        Request originalRequest = chain.request();
        Request newRequest = originalRequest.newBuilder()
                .header("X-Request-NetworkType", NetworkUtil.getNetworkTypeName(context))
                .header("X-Request-DeviceName", DeviceUtil.getDeviceName())
                .header("X-Request-DeviceResolution", DensityUtil.getScreenWidth(context) + "*" + DensityUtil.getScreenHeight(context))
                .header("X-Request-Vendor", DeviceUtil.getProductVendor())
                .header("X-Request-AppVersion", versionName)
                .header("X-Request-Time", currentTime)
                .header("X-Request-AppType", "public")
                .header("X-Request-Id", appType)
                .header("X-Nideshop-Token", PrefsUtil.getToken())
//                .header("X-Request-PushId", getPushId(context))
                .addHeader("X-Request-Sign", getSign(versionName, appType, currentTime, salt, originalRequest))
                .build();
        return chain.proceed(newRequest);

    }

    /**
     * 获取jpush的注册id
     *
     * @param context
     * @return
     */
//    private String getPushId(Context context) {
//        String pushId = PrefsUtil.getString(context, PrefsUtil.PUSH_ID, "");
//        if (TextUtil.isEmpty(pushId)) {
//            pushId = JPushInterface.getRegistrationID(context);
//            PrefsUtil.savaString(context, PrefsUtil.PUSH_ID, pushId);
//        }
//        return pushId;
//    }

    /**
     * 计算接口签名
     *
     * @return
     */
    private String getSign(String appVersionName, String appType, String timestamp, String salt, Request request) {
        final StringBuilder params = new StringBuilder();
        params.append(appVersionName)
                .append("&").append(appType)
                .append("&").append(timestamp)
                .append("&").append(salt)
                .append("&");
        final RequestBody requestBody = request.body();
        // 获取Query位置参数
        final HttpUrl httpUrl = request.url();
        final List<String> paramNames = new ArrayList<>();
        final ArrayMap<String, Object> parameters = new ArrayMap<>();
        boolean deleteLastChar = false;
        if (httpUrl.querySize() > 0) {
            Set<String> queryParameterNames = httpUrl.queryParameterNames();
            int j = 0;
            for (String paramName : queryParameterNames) {
                paramNames.add(paramName);
                parameters.put(paramName, httpUrl.queryParameterValue(j));
                j++;
            }
            deleteLastChar = true;
        }

        if (requestBody != null) {
            MediaType mediaType = requestBody.contentType();
            if (mediaType != null) {
                if (isText(mediaType)) {
                    String requestParams = bodyToString(request);
                    final int contentType = getMediaType(mediaType);
                    switch (contentType) {
                        case MEDIA_TYPE_FORM:
                            //表单请求,拼接每个字段
                            LogUtil.d(TAG, "requestParams=" + requestParams);
                            final List<String> fields = TextUtil.splitWith(requestParams, "&");
                            for (String field : fields) {
                                String[] split = field.split("=");
                                String key = URLDecoder.decode(split[0]);
                                String value = URLDecoder.decode(split[1]);
                                paramNames.add(key);
                                parameters.put(key, value);
                            }
                            break;
                        case MEDIA_TYPE_JSON:
                            final Map<String, String> map = JsonUtil.json2StringMap(requestParams);
                            CollectionUtil.iterator(map, new CollectionUtil.Processor<String, String>() {
                                @Override
                                public void process(String key, String value) {
                                    paramNames.add(key);
                                    parameters.put(key, value);
                                }
                            });
                            break;
                    }
                    deleteLastChar = true;
                }
            } else {
                LogUtil.w(TAG, "Request body maybe an file.");
            }
        }

        if (!paramNames.isEmpty()) {
            LogUtil.d(TAG, "请求参数排序前：" + paramNames);
            if (paramNames.size() > 1) {
                Collections.sort(paramNames);
                LogUtil.d(TAG, "请求参数排序后：" + paramNames);
            }
            for (String name : paramNames) {
                params.append(name).append("=").append(parameters.get(name)).append("&");
            }
        }
        String sign = MD5Util.md5Encrypt(deleteLastChar ? params.deleteCharAt(params.length() - 1).toString() : params.toString());
        LogUtil.d(TAG, "X-Request-Sign(before) = " + params.toString());
        LogUtil.d(TAG, "X-Request-Sign(after) = " + MD5Util.md5Encrypt(params.toString()));
        return sign;
    }

    private boolean isText(MediaType mediaType) {
        return getMediaType(mediaType) != 0;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }

    private int getMediaType(MediaType mediaType) {
        final String type = mediaType.type();
        if (type != null && type.equals("text")) {
            return MEDIA_TYPE_TEXT;
        }
        final String subtype = mediaType.subtype();
        if (subtype != null) {
            if ("x-www-form-urlencoded".equals(subtype)) {
                return MEDIA_TYPE_FORM;
            } else if (subtype.equals("json")) {
                return MEDIA_TYPE_JSON;
            } else if (subtype.equals("xml")) {
                return MEDIA_TYPE_XML;
            } else if (subtype.equals("html")) {
                return MEDIA_TYPE_HTML;
            } else if (subtype.equals("webviewhtml")) {
                return MEDIA_TYPE_WEB_VIEW_HTML;
            }
        }
        return 0;
    }
}
