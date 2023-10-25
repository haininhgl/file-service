package com.miraway.mss.web.rest.request;

import static com.miraway.mss.constants.Constants.STRING_MAX_LENGTH;

import com.miraway.mss.modules.common.validator.DatabaseIdConstraint;
import com.miraway.mss.modules.common.validator.OptionalDatabaseIdConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;

public class ObjectRequest {

    @Size(max = STRING_MAX_LENGTH)
    @NotBlank
    private String displayName;

    @DatabaseIdConstraint
    private String organizationId;

    @OptionalDatabaseIdConstraint
    private String parentId;

    public ObjectRequest() {}

    public ObjectRequest(String displayName, String organizationId, String parentId) {
        this.displayName = displayName;
        this.organizationId = organizationId;
        this.parentId = parentId;
    }

    public String getDisplayName() {
        return StringUtils.trim(displayName);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
