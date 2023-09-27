package com.miraway.mss.modules.object.dto;

import com.miraway.mss.modules.object.enumaration.ObjectType;
import com.miraway.mss.modules.object.enumaration.Type;
import java.time.Instant;


public class ObjectDTO {

    private String id;

    private String name;

    private String physicalName;

    private String organizationId;

    private String parentId;

    private String path;

    private String thumbnailPath;

    private Type type;

    private ObjectType objectType;

    private String objectFormat;

    private Long size;

    private boolean isDeleted = false;

    private Instant lastModifiedDate;

    public ObjectDTO() {
    }

    public ObjectDTO(String id, String name, String physicalName, String organizationId, String parentId,
                     String path, String thumbnailPath, Type type, ObjectType objectType, String objectFormat,
                     Long size, boolean isDeleted, Instant lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.physicalName = physicalName;
        this.organizationId = organizationId;
        this.parentId = parentId;
        this.path = path;
        this.thumbnailPath = thumbnailPath;
        this.type = type;
        this.objectType = objectType;
        this.objectFormat = objectFormat;
        this.size = size;
        this.isDeleted = isDeleted;
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysicalName() {
        return physicalName;
    }

    public void setPhysicalName(String physicalName) {
        this.physicalName = physicalName;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public String getObjectFormat() {
        return objectFormat;
    }

    public void setObjectFormat(String objectFormat) {
        this.objectFormat = objectFormat;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
