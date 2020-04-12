package config;

import java.util.Map;

public class MidurConfiguration implements Configuration{

    private Map<String, String> urlMap;

    //after this period a request will go to the plugin instead of taking from cache
    private Long cacheRelevanceTimeInMillis;

    //after this period of time a user will be deleted
    private Long userDeletionTimeInMillis;

    public MidurConfiguration() {
    }

    public Map<String, String> getUrlMap() {
        return urlMap;
    }

    public Long getCacheRelevanceTimeInMillis() {
        return cacheRelevanceTimeInMillis;
    }

    public Long getUserDeletionTimeInMillis() {
        return userDeletionTimeInMillis;
    }
}
