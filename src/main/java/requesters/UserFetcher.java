package requesters;

import models.Attribute;
import models.PluginResponse;
import models.User;

import java.util.List;
import java.util.Optional;

public class UserFetcher {
    private PluginResponseRequester responseRequester;

    public UserFetcher(PluginResponseRequester responseRequester) {
        this.responseRequester = responseRequester;
    }

    public Optional<User> updateUserFromPlugin(User user, String systemName) {
        Optional<PluginResponse> optionalPluginResponse = responseRequester.getPluginResponse(user, systemName);
        if(optionalPluginResponse.isPresent()){
            PluginResponse pluginResponse = optionalPluginResponse.get();
            user.updateTTL(systemName, pluginResponse.getTtl());
            user.updateAttributes(systemName, completeData(systemName, pluginResponse.getAttributeList()));
            return Optional.of(user);
        } else{
            return Optional.empty();
        }

    }

    private List<Attribute> completeData(String systemName, List<Attribute> attributeList) {
        attributeList.forEach(attribute -> {
            attribute.setAttributeId(systemName + ":" + attribute.getAttributeId());
            attribute.setMidurSystem(systemName);
        });

        return attributeList;
    }
}