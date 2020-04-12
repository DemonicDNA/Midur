package models;

import java.util.List;

public class PluginResponse {

    private Long ttl;
    private List<Attribute> attributeList;

    public PluginResponse() {
    }

    public Long getTtl() {
        return ttl;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }
}
