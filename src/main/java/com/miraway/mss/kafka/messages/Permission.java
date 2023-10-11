package com.miraway.mss.kafka.messages;

import com.miraway.mss.modules.common.entity.MultiLanguage;
import com.miraway.mss.security.MssGuard;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

import static com.miraway.mss.constants.Constants.STRING_MAX_LENGTH;

public class Permission {

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

    private MultiLanguage resourceName;

    private MultiLanguage actionName;

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
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
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
        return "PermissionMessage{" +
            "group='" + group + '\'' +
            ", resource='" + resource + '\'' +
            ", action='" + action + '\'' +
            ", authority='" + authority + '\'' +
            '}';
    }
}
