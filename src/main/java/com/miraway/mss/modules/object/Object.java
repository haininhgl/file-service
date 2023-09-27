package com.miraway.mss.modules.object;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Instant;

import static com.miraway.mss.constants.Constants.STRING_MAX_LENGTH;

public class Object {

    @Id
    private String id;

    @Size(max = STRING_MAX_LENGTH)
    @NotEmpty
    @Indexed(unique = true)
    private String name;

    @Size(max = STRING_MAX_LENGTH)
    @NotEmpty
    private String physicalName;

    @Size(max = STRING_MAX_LENGTH)
    @NotEmpty
    private String organizationId;

    @Size(max = STRING_MAX_LENGTH)
    @NotEmpty
    private String parentId;

    @Size(max = STRING_MAX_LENGTH)
    private String path;

    @Size(max = STRING_MAX_LENGTH)
    private String thumbnailPath;

    private Type type;

    private ObjectType objectType;

    private String objectFormat;

    @Size(max = STRING_MAX_LENGTH)
    private Long size;

    private boolean isDelete;

    private Instant lastModifiedDate;

    public Object() {
    }

    public Object(String id, String name, String physicalName, String organizationId, String parentId,
                  String path, String thumbnailPath, Type type, ObjectType objectType, String objectFormat,
                  Long size, boolean isDelete, Instant lastModifiedDate) {
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
        this.isDelete = isDelete;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
