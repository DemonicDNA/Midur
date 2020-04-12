package models;

public enum MidurResultStatusReason {

    Updated,
    OutdatedByCacheRelevance,
    OutdatedByTTLAndCacheRelevance,
    UserNotFromCache,
    SystemNameNotConfigured,
    FailedUpdatingUserUsingKnownAttributes

}
