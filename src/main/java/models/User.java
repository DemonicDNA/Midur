package models;

import config.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User {

    private String userId;
    private Map<String, List<Attribute>> systemToAttributes;

    //millis from 1970 utc
    private Map<String, Long> systemNameToTTL;

    //millis from 1970 utc
    private Long lastQueryTime;

    //millis from 1970 utc
    private Map<String, Long> systemToLastUpdateTime;

    public User(String userId, Configuration configuration) {
        this.userId = userId;
        systemToAttributes = new ConcurrentHashMap<>();
        systemNameToTTL = new ConcurrentHashMap<>();
        systemToLastUpdateTime = new ConcurrentHashMap<>();
        configuration.getUrlMap().keySet().forEach(systemName -> {
            systemNameToTTL.put(systemName, Long.MIN_VALUE);
            systemToAttributes.put(systemName, Collections.synchronizedList(new ArrayList<>()));
            systemToLastUpdateTime.put(systemName, Long.MIN_VALUE);
        });
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, List<Attribute>> getSystemToAttributes() {
        return systemToAttributes;
    }

    public Long getLastQueryTime() {
        return lastQueryTime;
    }

    public void setLastQueryTime(Long lastQueryTime) {
        this.lastQueryTime = lastQueryTime;
    }

    public Map<String, Long> getSystemNameToTTL() {
        return systemNameToTTL;
    }

    public void updateAttributes(String systemName, List<Attribute> attributes) {
        systemToAttributes.put(systemName, attributes);
    }

    public void updateTTL(String systemName, Long ttl) {
        systemNameToTTL.put(systemName, ttl);
    }

    public Map<String, Long> getSystemToLastUpdateTime() {
        return systemToLastUpdateTime;
    }

    public void setSystemToLastUpdateTime(Map<String, Long> systemToLastUpdateTime) {
        this.systemToLastUpdateTime = systemToLastUpdateTime;
    }
}
