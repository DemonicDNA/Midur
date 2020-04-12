package deleter;

import caches.Cache;
import config.Configuration;
import models.User;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UserCacheDeleter {

    private final Cache<String, User> userCache;
    private Configuration configuration;

    public UserCacheDeleter(Cache<String, User> userCache, Configuration configuration) {
        this.userCache = userCache;
        this.configuration = configuration;
    }


    public void start(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::deleteCycle, 0, 10, TimeUnit.MINUTES);
    }

    private void deleteCycle() {
        synchronized (userCache) {
            try {
                userCache.keySet().ifPresent(userIds -> userIds.forEach(userId -> {
                    userCache.get(userId).ifPresent(user -> {
                        if (isUserNeedToBeDeleted(user)) {
                            userCache.remove(user.getUserId());
                        }
                    });
                }));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean isUserNeedToBeDeleted(User user) {
        long now = OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
        return user.getLastQueryTime() + configuration.getUserDeletionTimeInMillis() < now;
    }

}
