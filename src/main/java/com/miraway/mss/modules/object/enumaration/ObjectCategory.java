package com.miraway.mss.modules.object.enumaration;

public enum ObjectCategory {
    VIDEO("VIDEO"),
    IMAGE("IMAGE"),
    DOCUMENT("DOCUMENT"),
    AUDIO("AUDIO"),
    APPLICATION("APPLICATION");

    private final String objectCategory;

    ObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }
}
