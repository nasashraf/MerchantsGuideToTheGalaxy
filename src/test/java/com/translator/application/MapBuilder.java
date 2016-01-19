package com.translator.application;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K,V> {

    private Map<K,V> resultMap;

    public MapBuilder() {
        this.resultMap = new HashMap<K, V>();
    }

    public static<K,V> MapBuilder aMapBuilder() {
        return new MapBuilder<K,V>();
    }

    public MapBuilder<K,V> put(K key, V value) {
        resultMap.put(key, value);
        return this;
    }

    public Map<K,V> build() {
        return resultMap;
    }

}
