package caches;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache<K,V> implements Cache<K,V> {

    private Map<K, V> inMemoryCache = new ConcurrentHashMap<>();

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(inMemoryCache.get(key));
    }

    @Override
    public Optional<V> put(K key, V value) {
        return Optional.ofNullable(inMemoryCache.put(key, value));
    }

    public Optional<Set<K>> keySet(){
        return Optional.of(inMemoryCache.keySet());
    }

    @Override
    public Optional<V> remove(K key) {
        return Optional.ofNullable(inMemoryCache.remove(key));
    }

    @Override
    public Optional<Collection<V>> values() {
        return Optional.of(inMemoryCache.values());
    }
}
