package models;

public class Action {

    private String name;
    private String restriction;

    public Action() {
    }

    public Action(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRestriction() {
        return restriction;
    }
}
