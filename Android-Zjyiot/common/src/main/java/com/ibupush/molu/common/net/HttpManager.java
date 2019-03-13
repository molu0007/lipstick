package com.ibupush.molu.common.net;


import android.support.annotation.NonNull;

import com.ibupush.molu.common.BuildConfig;
import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.TextUtil;
import com.ibupush.molu.common.util.TimeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求的封装类(Retrofit)
 * Created by 曾丽 on 2017/6/23.
 * <p>
 * <h3>示例：</h3>
 * <p>
 * <p>1.将HTTP API 转换成JAVA接口,如用户模块的登录接口
 * <p>
 * <pre>
 * public interface UserService {
 *   &#64;FormUrlEncoded
 *   &#64;POST("user/login")
 *   Call&lt;RespInfo&lt;User&gt;&gt; login(@Field("account") String account, @Field("password") String pwd);
 * }
 * </pre>
 * <p>
 * <p>2.生成实现了UserService的实例,调用相关方法得到请求对象call
 * <p>
 * <pre>
 * UserService service = HttpManager.create(UserService.class);
 * Call&lt;RespInfo&lt;User&gt;&gt; call = service.login("act", "pwd", "phone");
 * </pre>
 * <p>
 * <p>3.call可以执行同步或异步请求
 * <p>
 * <pre>
 * call.enqueue(new MyCallBack&lt;User&gt;() {
 * ...
 *  });
 * </pre>
 */
public class HttpManager {
    /**
     * OkHttpClient实例,单例模式
     */
    private static OkHttpClient sClient;

    /**
     * Retrofit实例,单例模式
     */
    private static Retrofit sRetrofit;

    /**
     * 拦截器实现自定义请求头
     */
    private static Interceptor sHeadIntercepter;

    private static Interceptor sStethoInterceptor;
    //主机地址
    private static String sBaseUrl;

    /**
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     * For example:
     * <pre>
     * public interface CategoryService {
     *   &#64;POST("category/{cat}/")
     *   Call&lt;List&lt;Item&gt;&gt; categoryList(@Path("cat") String a, @Query("page") int b);
     * }
     * </pre>
     */
    public static <T> T create(@NonNull Class<T> service) {
        return buildRetrofit().create(service);
    }

    /**
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     * For example:
     * <pre>
     * public interface CategoryService {
     *   &#64;POST("category/{cat}/")
     *   Call&lt;List&lt;Item&gt;&gt; categoryList(@Path("cat") String a, @Query("page") int b);
     * }
     * </pre>
     *
     * @param service        网络接口定义
     * @param connectTimeout 客户端和服务器建立连接的超时时间(单位:秒)
     * @param readTimeout    客户端从服务器下载响应数据的超时时间(单位:秒)
     * @param writeTimeout   客户端上传数据到服务器的超时时间
     */
    public static <T> T create(@NonNull Class<T> service, long connectTimeout, long readTimeout, long writeTimeout) {
        OkHttpClient okHttpClient = buildOkClient().newBuilder().readTimeout(readTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS).build();
        //.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("183.239.164.68", 8876)))
        return buildRetrofit()
                .newBuilder()
                .client(okHttpClient)
                .build()
                .create(service);
    }

    /**
     * 构建Retrofit实例
     *
     * @return
     */
    private static Retrofit buildRetrofit() {
        if (sRetrofit == null) {
            synchronized (HttpManager.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl(sBaseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(buildOkClient())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    /**
     * 构建OkHttp实例
     *
     * @return
     */
    private static OkHttpClient buildOkClient() {
        if (sClient == null) {
            synchronized (HttpManager.class) {
                if (sClient == null) {
                    LoggerIntercepter loggingInterceptor = new LoggerIntercepter();
                    loggingInterceptor.setLevel(LoggerIntercepter.Level.BODY);
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .connectTimeout(15, TimeUnit.SECONDS);
                    if (sHeadIntercepter != null) {
                        builder.addInterceptor(sHeadIntercepter);
                    }
                    //需要先加请求头拦截器,后加日志拦截器,才能打印自定义请求头
                    //日志使用addInterceptor,不用addNetworkInterceptor,防止外网环境gzip压缩导致的日志无法打印
                    builder.addInterceptor(loggingInterceptor);
                    if (sStethoInterceptor != null) {
                        builder.addInterceptor(sStethoInterceptor);
                    }


                    sClient = builder.build();
                }
            }
        }
        return sClient;
    }

    /**
     * faceBook网络拦截器
     *
     * @param interceptor
     */
    public static void setStethoInterceptor(Interceptor interceptor) {
        sStethoInterceptor = interceptor;
    }

    /**
     * 请求请求头拦截器
     *
     * @param interceptor
     */
    public static void setHeaderIntercepter(Interceptor interceptor) {
        sHeadIntercepter = interceptor;
    }

    /**
     * 设置主机地址
     *
     * @param baseUrl
     */
    public static void setBaseUrl(String baseUrl) {
        sBaseUrl = baseUrl;
    }

    /**
     * 下载文件
     *
     * @param url      文件URL
     * @param saveDir  下载文件保存路径
     * @param callback 下载监听回调
     */
    public static void downloadFile(@NonNull final String url, @NonNull final String saveDir, final String newFileName, @NonNull final DownloadCallback callback) {
        final ApiService apiService = create(ApiService.class);
        final Call<ResponseBody> call = apiService.downloadFile(url);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (BuildConfig.DEBUG) {
                        LogUtil.d("onResponse -> " + "server contacted and has file");
                    }
                    String fileName;
                    if (TextUtil.isEmpty(newFileName)) {
                        fileName = TimeUtil.getCurrentTime("yyyy-MM-dd") + "_"
                                + TimeUtil.getCurrentTimeMillis() + url.substring(url.lastIndexOf("."), url.length());
                    } else {
                        fileName = newFileName + url.substring(url.lastIndexOf("."), url.length());
                    }
                    boolean writtenToDisk = false;
                    final ResponseBody body = response.body();
                    try {
                        String absoluteFileName = saveDir.endsWith(File.separator) ? saveDir + fileName : saveDir + File.separator + fileName;
                        File file = new File(absoluteFileName);
                        InputStream inputStream = null;
                        OutputStream outputStream = null;
                        try {
                            byte[] fileReader = new byte[4096];
                            long fileSize = body.contentLength();
                            long fileSizeDownloaded = 0;
                            inputStream = body.byteStream();
                            outputStream = new FileOutputStream(file);
                            while (true) {
                                int read = inputStream.read(fileReader);
                                if (read == -1) {
                                    break;
                                }
                                outputStream.write(fileReader, 0, read);
                                fileSizeDownloaded += read;
                                if (BuildConfig.DEBUG) {
                                    LogUtil.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                                }
                            }
                            outputStream.flush();
                            writtenToDisk = true;
                        } catch (IOException e) {
                            writtenToDisk = false;
                            if (BuildConfig.DEBUG) {
                                LogUtil.d(e.getMessage());
                            }
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        }
                    } catch (IOException e) {
                        writtenToDisk = false;
                        if (BuildConfig.DEBUG) {
                            LogUtil.d(e.getMessage());
                        }
                    }

                    if (BuildConfig.DEBUG) {
                        LogUtil.d("file download was a success? " + writtenToDisk);
                    }
                    if (writtenToDisk) {
                        if (saveDir.endsWith(File.separator)) {
                            callback.onSuccess(saveDir + fileName);
                        } else {
                            callback.onSuccess(saveDir + File.separator + fileName);
                        }
                    } else {
                        if (BuildConfig.DEBUG) {
                            LogUtil.d("File write to disk failure.");
                        }
                        callback.onFailure("File write to disk failure.");
                    }
                } else {
                    if (BuildConfig.DEBUG) {
                        LogUtil.d("onResponse -> " + "server contact failed");
                    }
                    callback.onFailure("server contact failed.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (BuildConfig.DEBUG) {
                    LogUtil.e("onFailure -> " + t.getMessage());
                }
                callback.onFailure(t.getMessage());
            }
        });
    }
}