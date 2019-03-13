package com.ibupush.molu.common.util;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;

/**
 * JSON相关工具类
 * Created by Shyky on 2017/6/23.
 */
public final class JsonUtil {
    private static Gson gson;

    private JsonUtil() {

    }

    private static void initJsonConverter() {
        if (gson == null) {
            LogUtil.d("自定义gson转换适配器...");
            // 自定义Gson转换适配器TypeAdapter
            gson = new GsonBuilder().registerTypeAdapter(
                    new TypeToken<TreeMap<String, Object>>() {
                    }.getType(),
                    new JsonDeserializer<TreeMap<String, Object>>() {
                        @Override
                        public TreeMap<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            TreeMap<String, Object> treeMap = new TreeMap<>();
                            JsonObject jsonObject = json.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                            String key;
                            JsonElement value;
                            for (Map.Entry<String, JsonElement> entry : entrySet) {
                                key = entry.getKey();
                                value = entry.getValue();
                                treeMap.put(key, value.toString());
                            }
                            return treeMap;
                        }
                    })
                    .create();
        }
    }

    public static String toJsonString(@NonNull Object object) {
        if (object != null) {
            final Gson gson = new Gson();
            return gson.toJson(object);
        }
        return null;
    }

    public static <K, V> String toJsonString(@NonNull Map<K, V> map) {
        if (map != null && !map.isEmpty()) {
            final Gson gson = new Gson();
            return gson.toJson(map);
        }
        return null;
    }

    public static <K, V> String toJsonString(@NonNull ConcurrentMap<K, V> map) {
        if (map != null && !map.isEmpty()) {
            final Gson gson = new Gson();
            return gson.toJson(map);
        }
        return null;
    }

    public static <T> T json2Entity(String json, Class<T> clz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clz);
    }

    public static <K, V> Map<K, V> json2Map(@NonNull String json) {
        if (TextUtil.isEmpty(json))
            return null;
        initJsonConverter();
        return gson.fromJson(json, new TypeToken<TreeMap<K, V>>() {
        }.getType());
    }

    public static Map<String, String> json2StringMap(@NonNull String json) {
        if (TextUtil.isEmpty(json))
            return null;
        Map<String, String> map = new HashMap<>();
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        String key;
        JsonElement value;
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            key = entry.getKey();
            value = entry.getValue();
            if (value instanceof JsonObject) {
                map.put(key, jsonElement.getAsJsonObject().get(key).toString());
            } else if (value instanceof JsonArray) {
                map.put(key, jsonElement.getAsJsonObject().get(key).toString());
            } else {
                map.put(key, value.getAsString());
            }
        }
        return map;
    }

    public static <K, V> ConcurrentMap<K, V> json2ConcurrentMap(@NonNull String json) {
        if (TextUtil.isEmpty(json))
            return null;
        Gson gson = new Gson();
        return gson.fromJson(json, ConcurrentMap.class);
    }
    public static JSONObject getObject(String json) {
        if (json==null){
            return null;
        }
        JSONObject object = null;
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }
}