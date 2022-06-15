package com.cm6121.countWord.utils;

import java.util.*;

public class MapUtil {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, Boolean isAscending) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        if (isAscending) {
            list.sort(Map.Entry.comparingByValue());
        } else {
            list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));
        }

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}