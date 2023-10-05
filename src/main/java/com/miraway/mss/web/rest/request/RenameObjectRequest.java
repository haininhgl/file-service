package com.miraway.mss.web.rest.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.miraway.mss.constants.Constants.STRING_MAX_LENGTH;

public class RenameObjectRequest {

    @Size(max = STRING_MAX_LENGTH)
    @NotBlank
    private String displayName;

    public RenameObjectRequest() {
    }

    public RenameObjectRequest(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
