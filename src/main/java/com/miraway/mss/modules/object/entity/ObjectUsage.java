package com.miraway.mss.modules.object.entity;

import static com.miraway.mss.constants.Constants.STRING_MAX_LENGTH;

import com.miraway.mss.modules.common.entity.AbstractAuditingEntity;
import com.miraway.mss.modules.common.validator.DatabaseIdConstraint;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "objectUsages")
public class ObjectUsage extends AbstractAuditingEntity<String> {

    @Id
    private String id;

    @NotNull
    @DBRef
    private Object object;

    private boolean isDeletable = false;

    @NotBlank
    @Size(max = STRING_MAX_LENGTH)
    private String clientService;

    @DatabaseIdConstraint
    private String clientId;

    @NotBlank
    @Size(max = STRING_MAX_LENGTH)
    private String clientName;

    public ObjectUsage() {}

    public ObjectUsage(String id, Object object, boolean isDeletable, String clientService, String clientId, String clientName) {
        this.id = id;
        this.object = object;
        this.isDeletable = isDeletable;
        this.clientService = clientService;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isDeletable() {
        return isDeletable;
    }

    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }

    public String getClientService() {
        return clientService;
    }

    public void setClientService(String clientService) {
        this.clientService = clientService;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ObjectUsage)) {
            return false;
        }

        ObjectUsage that = (ObjectUsage) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return (
            "ObjectUsage{" +
            "id='" +
            id +
            '\'' +
            ", object=" +
            object +
            ", isDeletable=" +
            isDeletable +
            ", clientService='" +
            clientService +
            '\'' +
            ", clientId='" +
            clientId +
            '\'' +
            ", clientName='" +
            clientName +
            '\'' +
            '}'
        );
    }
}
