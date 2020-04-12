package models;

import java.util.List;

public class MidurResult {

    private List<Attribute> attributeList;
    private MidurResultStatusReason midurResultStatusReason;

    public MidurResult(List<Attribute> attributeList, MidurResultStatusReason midurResultStatusReason) {
        this.attributeList = attributeList;
        this.midurResultStatusReason = midurResultStatusReason;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public MidurResultStatusReason getMidurResultStatusReason() {
        return midurResultStatusReason;
    }
}
