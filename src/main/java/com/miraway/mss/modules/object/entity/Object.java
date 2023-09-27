package com.miraway.mss.modules.object.entity;

import com.miraway.mss.modules.common.entity.AbstractAuditingEntity;
import com.miraway.mss.modules.common.validator.DatabaseIdConstraint;
import com.miraway.mss.modules.object.enumaration.ObjectCategory;
import com.miraway.mss.modules.object.enumaration.ObjectType;
import com.mongodb.lang.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Objects;

import static com.miraway.mss.constants.Constants.STRING_MAX_LENGTH;

@Document(collection = "objects")
public class Object extends AbstractAuditingEntity<String> {
    @Id
    private String id;

    @Size(max = STRING_MAX_LENGTH)
    @NotBlank
    private String name;

    @Size(max = STRING_MAX_LENGTH)
    @NotBlank
    private String physicalName;

    @DatabaseIdConstraint
    private String organizationId;

    @Nullable
    private String parentId;

    @Size(max = STRING_MAX_LENGTH)
    @NotBlank
    private String path;

    @Size(max = STRING_MAX_LENGTH)
    @NotBlank
    private String thumbnailPath;

    @NotNull
    private ObjectType objectType;

    @Nullable
    private ObjectCategory category;

    @Nullable
    private String extension;

    private String mimeType;

    private Long size;

    private boolean isDeleted = false;

    public Object() {
    }

    public Object(String id, String name, String physicalName, String organizationId, @Nullable String parentId,
                  String path, String thumbnailPath, ObjectType objectType, @Nullable ObjectCategory category,
                  @Nullable String extension, String mimeType, Long size, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.physicalName = physicalName;
        this.organizationId = organizationId;
        this.parentId = parentId;
        this.path = path;
        this.thumbnailPath = thumbnailPath;
        this.objectType = objectType;
        this.category = category;
        this.extension = extension;
        this.mimeType = mimeType;
        this.size = size;
        this.isDeleted = isDeleted;
    }

    @Override
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

    @Nullable
    public String getParentId() {
        return parentId;
    }

    public void setParentId(@Nullable String parentId) {
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

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    @Nullable
    public ObjectCategory getCategory() {
        return category;
    }

    public void setCategory(@Nullable ObjectCategory category) {
        this.category = category;
    }

    @Nullable
    public String getExtension() {
        return extension;
    }

    public void setExtension(@Nullable String extension) {
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Object)) {
            return false;
        }
        return id != null && id.equals(((Object) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, physicalName, organizationId, parentId, path, thumbnailPath, objectType, category, extension, mimeType, size, isDeleted);
    }

    @Override
    public String toString() {
        return "Object{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", physicalName='" + physicalName + '\'' +
            ", organizationId='" + organizationId + '\'' +
            ", parentId='" + parentId + '\'' +
            ", path='" + path + '\'' +
            ", thumbnailPath='" + thumbnailPath + '\'' +
            ", objectType=" + objectType +
            ", category=" + category +
            ", extension='" + extension + '\'' +
            ", mimeType='" + mimeType + '\'' +
            ", size=" + size +
            ", isDeleted=" + isDeleted +
            '}';
    }
}
