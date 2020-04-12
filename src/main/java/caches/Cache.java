package caches;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface Cache<K, V> {

    Optional<V> get(K key);

    Optional<V> put(K key, V value);

    Optional<Set<K>> keySet();

    Optional<V> remove(K key);

    Optional<Collection<V>> values();
}
