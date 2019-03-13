package com.ibupush.molu.common.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Shyky on 2017/6/15.
 */
public final class CollectionUtil {
    public interface Processor<K, V> {
        void process(K key, V value);
    }

    private CollectionUtil() {

    }

    public static <T> boolean isEmpty(@NonNull List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <K, V> List<K> keySetAsList(@NonNull Map<K, V> map) {
        if (map != null) {
            final List<K> keys = new ArrayList<>();
            keys.addAll(map.keySet());
            return keys;
        }
        return null;
    }

    public static <K, V> List<V> valueAsList(@NonNull Map<K, V> map) {
        if (map != null) {
            final List<V> values = new ArrayList<>();
            for (Map.Entry<K, V> entry : map.entrySet()) {
                values.add(entry.getValue());
            }
            return values;
        }
        return null;
    }

    public static <K, V> K getKey(@NonNull Map<K, V> map, int index) {
        final List<K> keys = keySetAsList(map);
        return keys.get(index);
    }

    public static <K, V> V getValue(@NonNull Map<K, V> map, int index) {
        final List<V> values = valueAsList(map);
        return values.get(index);
    }

    public static <K, V> void iterator(@NonNull ConcurrentMap<K, V> map, Processor<K, V> callback) {
        if (map != null && !map.isEmpty() && callback != null) {
            final Set<Map.Entry<K, V>> entries = map.entrySet();
            final Iterator<Map.Entry<K, V>> it = entries.iterator();
            Map.Entry<K, V> entry;
            while (it.hasNext()) {
                entry = it.next();
                callback.process(entry.getKey(), entry.getValue());
            }
        }
    }

    public static <K, V> void iterator(@NonNull Map<K, V> map, Processor<K, V> callback) {
        if (map != null && !map.isEmpty() && callback != null) {
            final Set<Map.Entry<K, V>> entries = map.entrySet();
            final Iterator<Map.Entry<K, V>> it = entries.iterator();
            Map.Entry<K, V> entry;
            while (it.hasNext()) {
                entry = it.next();
                callback.process(entry.getKey(), entry.getValue());
            }
        }
    }

    public static <T> String splitWith(@NonNull List<T> list, @NonNull String splitStr) {
        if (isEmpty(list))
            return null;
        else if (splitStr == null || splitStr.length() == 0)
            throw new IllegalArgumentException("The argument splitStr can not be empty.");
        final StringBuilder str = new StringBuilder();
        for (T item : list) {
            str.append(item).append(splitStr);
        }
        return str.deleteCharAt(str.length() - 1).toString();
    }
}