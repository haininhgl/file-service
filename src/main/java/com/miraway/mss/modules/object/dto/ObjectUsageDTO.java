package com.miraway.mss.modules.object.dto;

import com.miraway.mss.modules.object.entity.Object;

public class ObjectUsageDTO {

    private String id;

    private Object object;

    private boolean isDeletable = false;

    private String clientService;

    private String clientId;

    private String clientName;

    public ObjectUsageDTO() {
    }

    public ObjectUsageDTO(String id, Object object, boolean isDeletable,
                          String clientService, String clientId, String clientName) {
        this.id = id;
        this.object = object;
        this.isDeletable = isDeletable;
        this.clientService = clientService;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isDeletable() {
        return isDeletable;
    }

    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }

    public String getClientService() {
        return clientService;
    }

    public void setClientService(String clientService) {
        this.clientService = clientService;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
