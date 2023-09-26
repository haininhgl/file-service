package com.miraway.mss.modules.common.entity;

import static com.miraway.mss.constants.Constants.STRING_MAX_LENGTH;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Size;

public class MultiLanguage {

    @Size(max = STRING_MAX_LENGTH)
    private String en = "";

    @Size(max = STRING_MAX_LENGTH)
    private String vi = "";

    public MultiLanguage() {}

    @JsonCreator
    public MultiLanguage(@JsonProperty("en") String en, @JsonProperty("vi") String vi) {
        this.en = en;
        this.vi = vi;
    }

    public MultiLanguage(@JsonProperty("en") String en) {
        this.en = en;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getVi() {
        return vi;
    }

    public void setVi(String vi) {
        this.vi = vi;
    }
}
