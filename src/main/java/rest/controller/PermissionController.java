package rest.controller;

import caches.Cache;
import config.Configuration;
import models.MidurResult;
import models.MidurResultStatusReason;
import models.User;
import requesters.UserFetcher;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class PermissionController {

    private UserFetcher userFetcher;
    private final Cache<String, User> userCache;
    private Configuration midurConfiguration;

    public PermissionController(UserFetcher userFetcher, Cache<String, User> userCache, Configuration midurConfiguration) {
        this.userFetcher = userFetcher;
        this.userCache = userCache;
        this.midurConfiguration = midurConfiguration;
    }

    public MidurResult calculateMidurResult(String userId, String systemName, boolean forced) {
        synchronized (userCache) {

            if (midurConfiguration.getUrlMap().get(systemName) == null) {
                return new MidurResult(null, MidurResultStatusReason.SystemNameNotConfigured);
            }

            User user = userCache.get(userId).orElseGet(() -> new User(userId, midurConfiguration));
            MidurResultStatusReason midurResultStatusReason = MidurResultStatusReason.Updated;
            if (user.getSystemToLastUpdateTime().get(systemName) == null || !isSystemUpToDate(user, systemName) || forced) {
                Optional<User> optionalUpdatedUser = userFetcher.updateUserFromPlugin(user, systemName);

                //if we failed updating from plugin for some reason
                boolean wasUserUpdatedByPluginSuccessfully = optionalUpdatedUser.isPresent();
                if (!wasUserUpdatedByPluginSuccessfully) {
                    Long ttlBySystem = user.getSystemNameToTTL().get(systemName);
                    if (ttlBySystem == Long.MIN_VALUE) {
                        midurResultStatusReason = MidurResultStatusReason.FailedUpdatingUserUsingKnownAttributes;
                    } else {
                        midurResultStatusReason = MidurResultStatusReason.OutdatedByCacheRelevance;
                        if (ttlBySystem < OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli()) {
                            midurResultStatusReason = MidurResultStatusReason.OutdatedByTTLAndCacheRelevance;
                        }
                    }
                }
            }

            user.setLastQueryTime(OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli());
            userCache.put(user.getUserId(), user);
            Optional<User> optionalUpdatedUser = userCache.get(userId);
            if (optionalUpdatedUser.isPresent()) {
                return new MidurResult(optionalUpdatedUser.get().getSystemToAttributes().get(systemName), midurResultStatusReason);
            } else {
                return new MidurResult(user.getSystemToAttributes().get(systemName), MidurResultStatusReason.UserNotFromCache);
            }
        }
    }

    private boolean isSystemUpToDate(User user, String systemName) {
        long now = OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
        Long userLastUpdateTimeBySystem = user.getSystemToLastUpdateTime().get(systemName);
        return userLastUpdateTimeBySystem + midurConfiguration.getCacheRelevanceTimeInMillis() > now;
    }

}
