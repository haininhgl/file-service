package com.miraway.mss.modules.object.enumaration;

public enum ObjectType {

    VIDEO("VIDEO"),
    IMAGE("IMAGE"),
    DOCUMENT("DOCUMENT"),
    AUDIO("AUDIO"),
    APPLICATION("APPLICATION");

    private final String objectType;

    ObjectType(String objectType) {
        this.objectType = objectType;
    }
}
