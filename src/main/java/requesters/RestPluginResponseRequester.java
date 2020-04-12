package requesters;

import config.Configuration;
import models.PluginResponse;
import models.User;
import rest.client.RestClient;

import java.util.Map;
import java.util.Optional;

public class RestPluginResponseRequester implements PluginResponseRequester {

    private RestClient<PluginResponse> client;
    private final Map<String, String> urlMap;

    public RestPluginResponseRequester(RestClient<PluginResponse> client, Configuration config) {
        this.client = client;
        this.urlMap = config.getUrlMap();
    }


    @Override
    public Optional<PluginResponse> getPluginResponse(User user, String systemName) {
        String systemUrl = urlMap.get(systemName);
        if(systemUrl == null){
            return Optional.empty();
        }
        return client.get(systemUrl + "/" + user.getUserId());
    }


}
