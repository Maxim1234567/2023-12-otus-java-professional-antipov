package ru.otus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class CacheMap<K, V> {
    private final Map<K, V> cache;

    public CacheMap() {
        cache = new HashMap<>();
    }

    public V getOrPut(K key, Supplier<V> supplier) {
        V result = cache.get(key);

        if (Objects.nonNull(result))
            return result;

        V calculate = supplier.get();

        cache.put(key, calculate);

        return calculate;
    }
}
