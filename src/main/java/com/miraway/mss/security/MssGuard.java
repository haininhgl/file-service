package com.miraway.mss.security;

import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class MssGuard {

    public static final String WILDCARD_TOKEN = "*";
    public static final String PART_DIVIDER_TOKEN = ":";
    public static final String LEVEL_DIVIDER_TOKEN = ".";

    /**
     * At this time, Spring Security team doesn't have plan to support PermissionEvaluator overriding for reactive
     * We provide this @Bean as alternative solution.
     * Usage: @PreAuthorize("@mssGuard.hasPermission(#authentication, 'resource', 'action')")
     * It works with @PostAuthorize as well
     *
     * @param authentication
     * @param requestingPermission
     * @return
     */
    public boolean hasPermission(Authentication authentication, String requestingPermission) {
        if (authentication == null || StringUtils.isBlank(requestingPermission)) {
            return false;
        }

        // Get list of authorities
        final Set<String> authorities = authentication
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());

        return matchingPermission(authorities, requestingPermission);
    }

    /**
     * Check if current user has any permissions matching with provided list
     *
     * @param authentication
     * @param permissions
     * @return
     */
    public boolean hasAnyPermissions(Authentication authentication, String... permissions) {
        if (authentication == null || permissions.length == 0) {
            return false;
        }

        // Get list of authorities
        final Set<String> authorities = authentication
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());

        // Check exact match first
        for (String permission : permissions) {
            if (authorities.contains(permission)) {
                return true;
            }
        }

        // Match by pattern
        for (String permission : permissions) {
            if (matchingPermission(authorities, permission)) {
                return true;
            }
        }

        return false;
    }

    private boolean matchingPermission(Set<String> authorities, String checkingPermission) {
        // Check exact match first
        if (authorities.contains(checkingPermission)) {
            return true;
        }

        // Check for pattern matching
        // security.organization:create is split into 2 parts [security.organization, create]
        String[] parts = StringUtils.split(checkingPermission, PART_DIVIDER_TOKEN);
        if (parts == null || parts.length != 2) {
            return false;
        }

        final String checkingResource = parts[0];

        for (String authority : authorities) {
            // security.organization:* is split into 2 parts [security.organization, *]
            final String[] authorityParts = authority.split(PART_DIVIDER_TOKEN);
            if (authorityParts.length < 2) {
                continue;
            }

            // Same resource but the action is "*" then would be a match
            if (authorityParts[0].equalsIgnoreCase(checkingResource) && StringUtils.equals(authorityParts[1], WILDCARD_TOKEN)) {
                return true;
            }

            // The authority is at higher level, for example [security:*] while the checking permission is [security.organization:create]
            // then we will have to check if the action is [*]
            if (checkingResource.startsWith((authorityParts[0] + LEVEL_DIVIDER_TOKEN)) && StringUtils.equals(authorityParts[1], "*")) {
                return true;
            }
        }

        return false;
    }
}
