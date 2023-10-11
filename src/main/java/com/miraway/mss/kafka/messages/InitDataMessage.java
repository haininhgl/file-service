package com.miraway.mss.kafka.messages;

import java.util.HashSet;
import java.util.Set;

public class InitDataMessage {

    private Set<Permission> permissions = new HashSet<>();

    public InitDataMessage() {}

    public InitDataMessage(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
