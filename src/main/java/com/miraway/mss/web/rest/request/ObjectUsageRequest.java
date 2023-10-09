package com.miraway.mss.web.rest.request;

import com.miraway.mss.modules.common.validator.DatabaseIdConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.miraway.mss.constants.Constants.STRING_MAX_LENGTH;

public class ObjectUsageRequest {

    @NotBlank
    private String objectId;

    @NotBlank
    @Size(max = STRING_MAX_LENGTH)
    private String clientService;

    @DatabaseIdConstraint
    private String clientId;

    @NotBlank
    @Size(max = STRING_MAX_LENGTH)
    private String clientName;

    public ObjectUsageRequest() {
    }

    public ObjectUsageRequest(String objectId, String clientService, String clientId, String clientName) {
        this.objectId = objectId;
        this.clientService = clientService;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
