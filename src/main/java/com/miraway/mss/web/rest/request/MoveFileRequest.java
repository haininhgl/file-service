package com.miraway.mss.web.rest.request;


import com.miraway.mss.modules.common.validator.DatabaseIdConstraint;
import com.miraway.mss.modules.common.validator.DatabaseIdListConstraint;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class MoveFileRequest {

    @NotBlank
    @DatabaseIdListConstraint
    private Set<String> idSources;

    @NotBlank
    @DatabaseIdConstraint
    private String idTarget;

    public MoveFileRequest() {
    }

    public MoveFileRequest(Set<String> idSources, String idTarget) {
        this.idSources = idSources;
        this.idTarget = idTarget;
    }

    public Set<String> getIdSources() {
        return idSources;
    }

    public void setIdSources(Set<String> idSources) {
        this.idSources = idSources;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }
}
