import caches.Cache;
import caches.InMemoryCache;
import config.Configuration;
import config.JsonResourceConfigurationProvider;
import deleter.UserCacheDeleter;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import models.User;
import requesters.RestPluginResponseRequester;
import requesters.UserFetcher;
import rest.PermissionResource;
import rest.client.UnirestClient;
import rest.controller.PermissionController;
import rest.server.MidurRestServerConfiguration;
import serialization.JacksonJsonReader;
import rest.UserResource;

import java.util.UUID;

public class MidurRestService extends Application<MidurRestServerConfiguration> {

    public static void main(String[] args) throws Exception {
        System.out.println(UUID.randomUUID());
        new MidurRestService().run(args);
    }

    @Override
    public void run(MidurRestServerConfiguration restServerConfig, Environment environment) {
        JsonResourceConfigurationProvider provider =
                new JsonResourceConfigurationProvider("configuration", new JacksonJsonReader());
        Configuration midurConfiguration = provider.provide().get();
        UserFetcher userFetcher = new UserFetcher(new RestPluginResponseRequester(new UnirestClient<>(), midurConfiguration));
        Cache<String, User> userCache = new InMemoryCache<>();
        new UserCacheDeleter(userCache, midurConfiguration).start();

        //register resources
        environment.jersey().register(new UserResource(userCache));
        environment.jersey().register(new PermissionResource(new PermissionController(userFetcher, userCache, midurConfiguration)));
    }

    @Override
    public void initialize(Bootstrap<MidurRestServerConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<MidurRestServerConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(MidurRestServerConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

}
