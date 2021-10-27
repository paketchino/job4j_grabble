package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    protected final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        SoftReference<V> softReferenceValue = new SoftReference<>(value);
        cache.put(key, softReferenceValue);
    }

    public V get(K key) {
        SoftReference<V> softReferenceValue = cache.get(key);
        if (cache.get(key) == null || cache.get(key).get() == null) {
            cache.put(key, softReferenceValue);
        }
        return softReferenceValue.get();
    }

    protected abstract V load(K key);
}