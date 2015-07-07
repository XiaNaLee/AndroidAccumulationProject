package com.accumulation.lee.utils.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by liyong on 15/5/6.
 */
public class MapUtil {

    /**
     * @param map
     * @return
     */
    public static <K, V> K key(Map<K, V> map) {
        if (map != null) {
            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
            if (iterator.hasNext()) {
                //效率高
                Map.Entry<K, V> entry = (Map.Entry) iterator.next();
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * @param map
     * @return
     */
    public static <K, V> V value(Map<K, V> map) {
        if (map != null) {
            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
            if (iterator.hasNext()) {
                //效率高
                Map.Entry<K, V> entry = (Map.Entry) iterator.next();
                return entry.getValue();
            }
        }
        return null;
    }


    /**
     * 将map的value转换成list集合
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> List<V> Map2VList(Map<K, V> map) {
        List<V> list = new ArrayList<>(map.values());
        return list;
    }

    /**
     * 将map的key转换成list集合
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> List<K> Map2KList(Map<K, V> map) {
        List<K> list = new ArrayList<>(map.keySet());
        return list;
    }

}
