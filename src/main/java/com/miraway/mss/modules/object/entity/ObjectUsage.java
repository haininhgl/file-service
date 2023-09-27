package com.miraway.mss.modules.object.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(collection = "objectUsages")
public class ObjectUsage {

    @Id
    private String id;

    @DBRef
    @NotBlank
    private Object object;

    private boolean isDeletable = false;

    @NotBlank
    private String clientService;

    @NotBlank
    private String clientId;

    @NotBlank
    private String clientName;

    public ObjectUsage() {
    }

    public ObjectUsage(String id, Object object, boolean isDeletable,
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
