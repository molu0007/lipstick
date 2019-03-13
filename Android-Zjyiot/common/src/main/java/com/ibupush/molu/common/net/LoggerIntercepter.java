package com.ibupush.molu.common.net;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.TextUtil;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * 网络日志拦截器
 * *An OkHttp interceptor which logs request and response information. Can be applied as an
 * {@linkplain OkHttpClient#interceptors() application interceptor} or as a {@linkplain
 * OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 * Created by 曾丽 on 2017/8/17.
 *
 * @author 曾丽
 */

public class LoggerIntercepter implements Interceptor {

    public static final String TAG = "OkHttp";

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        /**
         * A {@link Logger} defaults output appropriate for the current platform.
         */
        Logger DEFAULT = new Logger() {
            @Override
            public void log(String message) {
                Platform.get().log(INFO, message, null);
            }
        };
    }

    public LoggerIntercepter() {
        this(Logger.DEFAULT);
    }

    public LoggerIntercepter(Logger logger) {
//        this.logger = logger;
    }

//    private final Logger logger;

    private volatile Level level = Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public LoggerIntercepter setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        StringBuilder sbReq = new StringBuilder();
        StringBuilder sbResp = new StringBuilder();
        String jsonReq = "";
        String jsonResp = "";

        Level level = this.level;

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        sbReq.append(requestStartMessage).append("\n");

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    sbReq.append("Content-Type: ").append(requestBody.contentType()).append("\n");
                }
                if (requestBody.contentLength() != -1) {
                    sbReq.append("Content-Length: ").append(requestBody.contentLength()).append("\n");
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    sbReq.append(name).append(": ").append(headers.value(i)).append("\n");
                }
            }

            if (!logBody || !hasRequestBody) {
                sbReq.append("--> END ").append(request.method()).append("\n");
            } else if (bodyEncoded(request.headers())) {
                sbReq.append("--> END ").append(request.method()).append(" (encoded body omitted)").append("\n");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                sbReq.append("\n");
                if (isPlaintext(buffer)) {
                    //请求体最大打印10k
                    String reqStr = buffer.size() < 10240 ? buffer.readString(charset) : buffer.readString(10240, charset);
                    sbReq.append(reqStr);
                    sbReq.append("--> END ").append(request.method()).append(
                            " (").append(requestBody.contentLength()).append("-byte body)").append("\n");
                    jsonReq = reqStr;
                } else {
                    sbReq.append("--> END ").append(request.method()).append(" (binary ")
                            .append(requestBody.contentLength()).append("-byte body omitted)").append("\n");
                }
            }
        }

        //打印请求日志
        LogUtil.d(TAG, sbReq.toString());
        //如果请求体是json,以json格式打印出来
        if (isJson(jsonReq))
            LogUtil.json(TAG, jsonReq);

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            LogUtil.w(TAG, "<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        sbResp.append("<-- ").append(response.code()).append(' ').append(response.message()).append(' ')
                .append(response.request().url()).append(" (").append(tookMs).append("ms").append(!logHeaders ? ", "
                + bodySize + " body" : "").append(')').append("\n");

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                sbResp.append(headers.name(i)).append(": ").append(headers.value(i)).append("\n");
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                sbResp.append("<-- END HTTP" + "\n");
            } else if (bodyEncoded(response.headers())) {
                sbResp.append("<-- END HTTP (encoded body omitted)" + "\n");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (!isPlaintext(buffer)) {
                    sbResp.append("\n");
                    sbResp.append("<-- END HTTP (binary ").append(buffer.size()).append("-byte body omitted)").append("\n");
                    LogUtil.d(TAG, sbResp.toString());
                    return response;
                }

                if (contentLength != 0) {
                    sbResp.append("\n");
                    String respStr = buffer.clone().readString(charset);
                    sbResp.append(respStr);
                    jsonResp = respStr;
                }

                sbResp.append("<-- END HTTP (").append(buffer.size()).append("-byte body)").append("\n");
            }
        }

        LogUtil.d(TAG, sbResp.toString());
        //如果响应体是json,打印返回的json数据
        if (isJson(jsonResp))
            LogUtil.json(TAG, jsonResp);

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    /**
     * 判断字符串是否为json类型
     *
     * @param json
     * @return
     */
    private boolean isJson(String json) {
        try {
            if (TextUtil.isEmpty(json))
                return false;
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

}
