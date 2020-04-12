package config;
import java.util.Optional;

public interface ConfigurationProvider {

    Optional<Configuration> provide();

}
