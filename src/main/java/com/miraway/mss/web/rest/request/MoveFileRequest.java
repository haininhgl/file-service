package com.miraway.mss.web.rest.request;


import com.miraway.mss.modules.common.validator.DatabaseIdConstraint;

import javax.validation.constraints.NotBlank;

public class MoveFileRequest {

    @NotBlank
    @DatabaseIdConstraint
    private String idSource;

    @NotBlank
    @DatabaseIdConstraint
    private String idTarget;

    public MoveFileRequest() {
    }

    public MoveFileRequest(String idSource, String idTarget) {
        this.idSource = idSource;
        this.idTarget = idTarget;
    }

    public String getIdSource() {
        return idSource;
    }

    public void setIdSource(String idSource) {
        this.idSource = idSource;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }
}
