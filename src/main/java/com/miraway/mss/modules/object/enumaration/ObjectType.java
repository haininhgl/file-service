package com.miraway.mss.modules.object.enumaration;

public enum ObjectType {
    FILE("FILE"),
    FOLDER("FOLDER");

    private final String objectType;

    ObjectType(String objectType) {
        this.objectType = objectType;
    }
}
