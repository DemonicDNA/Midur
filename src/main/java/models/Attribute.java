package models;


import java.util.List;
import java.util.Map;

public class Attribute {

    //not null
    private String attributeId;

    //Ex: Triangle, Source, Operational
    //not null
    private String attributeType;

    //Ex: Borer, Mirage, Nova, System
    //not null
    private String midurSystem;

    private Map<String, Object> properties;
    private List<Action> allowedActions;

    public Attribute() {
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public String getMidurSystem() {
        return midurSystem;
    }

    public void setMidurSystem(String midurSystem) {
        this.midurSystem = midurSystem;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public List<Action> getAllowedActions() {
        return allowedActions;
    }
}
