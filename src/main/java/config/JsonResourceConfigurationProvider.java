package config;


import serialization.JsonReader;

import java.io.IOException;
import java.util.Optional;

public class JsonResourceConfigurationProvider implements ConfigurationProvider {

    private final String path;
    private final JsonReader jsonReader;

    public JsonResourceConfigurationProvider(String configurationPath, JsonReader jsonReader) {
        this.path = configurationPath;
        this.jsonReader = jsonReader;
    }

    @Override
    public Optional<Configuration> provide() {
        try {
            return Optional.of(jsonReader.fromJson(JsonResourceConfigurationProvider.class.getClassLoader().getResourceAsStream(path), MidurConfiguration.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
