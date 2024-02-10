package ru.otus;

import java.util.HashMap;

public class CacheFactory {
    public static <K, V> CacheMap<K, V> create() {
        return new CacheMap(new HashMap());
    }
}
