package config;

import java.util.Map;

public interface Configuration {
    Map<String, String> getUrlMap();
    Long getCacheRelevanceTimeInMillis();
    Long getUserDeletionTimeInMillis();
}
