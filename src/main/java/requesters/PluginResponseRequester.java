package requesters;

import models.PluginResponse;
import models.User;

import java.util.Optional;

public interface PluginResponseRequester {

    Optional<PluginResponse> getPluginResponse(User user, String systemName);

}
