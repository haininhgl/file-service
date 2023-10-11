package com.miraway.mss.modules.object.dto.filter;

import com.miraway.mss.modules.common.validator.DatabaseIdListConstraint;
import com.miraway.mss.modules.object.enumaration.ObjectCategory;
import com.miraway.mss.modules.object.enumaration.ObjectType;

import java.util.HashSet;
import java.util.Set;


public class ObjectFilter {

    private String text = "";

    private Set<String> parentIds = new HashSet<>();

    @DatabaseIdListConstraint
    private Set<String> organizationIds = new HashSet<>();

    private Set<String> ids = new HashSet<>();

    private ObjectType type;

    private Set<ObjectCategory> objectCategory = new HashSet<>();

    public ObjectFilter() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<String> getParentIds() {
        return parentIds;
    }

    public void setParentIds(Set<String> parentIds) {
        this.parentIds = parentIds;
    }

    public Set<String> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(Set<String> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public Set<ObjectCategory> getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(Set<ObjectCategory> objectCategory) {
        this.objectCategory = objectCategory;
    }

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }
}
