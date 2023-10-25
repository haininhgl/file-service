package com.miraway.mss.modules.user.entity;

import static com.miraway.mss.constants.Constants.STRING_MAX_LENGTH;

import com.miraway.mss.modules.common.entity.AbstractAuditingEntity;
import com.miraway.mss.modules.common.entity.MultiLanguage;
import com.miraway.mss.security.MssGuard;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

public class Permission extends AbstractAuditingEntity<String> {

    @Id
    private String id;

    @NotBlank
    @Size(max = STRING_MAX_LENGTH)
    private String group;

    @NotBlank
    @Size(max = STRING_MAX_LENGTH)
    private String resource = MssGuard.WILDCARD_TOKEN;

    @NotBlank
    @Size(max = STRING_MAX_LENGTH)
    private String action = MssGuard.WILDCARD_TOKEN;

    private MultiLanguage groupName;

    @NotNull
    private MultiLanguage resourceName;

    @NotNull
    private MultiLanguage actionName;

    @NotNull
    @Indexed(unique = true)
    private String authority;

    private boolean isActivated = true;

    public Permission() {}

    public Permission(String group, String action, MultiLanguage groupName, MultiLanguage actionName) {
        this.resource = MssGuard.WILDCARD_TOKEN;
        this.resourceName = new MultiLanguage("", "");

        this.groupName = groupName;
        this.actionName = actionName;
        this.group = group;
        this.action = action;
        this.authority = group + MssGuard.PART_DIVIDER_TOKEN + action;
    }

    public Permission(
        String group,
        String resource,
        String action,
        MultiLanguage groupName,
        MultiLanguage resourceName,
        MultiLanguage actionName
    ) {
        this.groupName = groupName;
        this.resourceName = resourceName;
        this.actionName = actionName;

        this.group = group;
        this.resource = resource;
        this.action = action;

        if (resource.equals(MssGuard.WILDCARD_TOKEN)) {
            this.authority = group + MssGuard.PART_DIVIDER_TOKEN + action;
        } else {
            this.authority = group + MssGuard.LEVEL_DIVIDER_TOKEN + resource + MssGuard.PART_DIVIDER_TOKEN + action;
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public MultiLanguage getGroupName() {
        return groupName;
    }

    public void setGroupName(MultiLanguage groupName) {
        this.groupName = groupName;
    }

    public MultiLanguage getResourceName() {
        return resourceName;
    }

    public void setResourceName(MultiLanguage resourceName) {
        this.resourceName = resourceName;
    }

    public MultiLanguage getActionName() {
        return actionName;
    }

    public void setActionName(MultiLanguage actionName) {
        this.actionName = actionName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.authority);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Permission permission = (Permission) obj;

        if (StringUtils.isBlank(permission.getAuthority()) || StringUtils.isBlank(this.getAuthority())) {
            return false;
        }

        return StringUtils.equalsIgnoreCase(this.getAuthority(), permission.getAuthority());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Permission{" +
            "group='" + group + '\'' +
            ", resource='" + resource + '\'' +
            ", action='" + action + '\'' +
            ", authority='" + authority + '\'' +
            '}';
    }
}
