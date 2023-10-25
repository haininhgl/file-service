package com.miraway.mss.web.rest.request;

import com.miraway.mss.modules.common.validator.DatabaseIdConstraint;
import com.miraway.mss.modules.common.validator.DatabaseIdListConstraint;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotEmpty;

public class MoveFileRequest {

    @NotEmpty
    @DatabaseIdListConstraint
    private Set<String> sourceIds = new HashSet<>();

    @DatabaseIdConstraint
    private String idTarget;

    public MoveFileRequest() {}

    public MoveFileRequest(Set<String> sourceIds, String idTarget) {
        this.sourceIds = sourceIds;
        this.idTarget = idTarget;
    }

    public Set<String> getSourceIds() {
        return sourceIds;
    }

    public void setSourceIds(Set<String> sourceIds) {
        this.sourceIds = sourceIds;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }
}
