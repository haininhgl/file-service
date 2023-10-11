package com.miraway.mss.modules.organization.repository;

import com.miraway.mss.client.AuthorizedFeignClient;
import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.common.validator.DatabaseIdConstraint;
import com.miraway.mss.modules.common.validator.DatabaseIdListConstraint;
import com.miraway.mss.modules.organization.dto.OrganizationDTO;
import com.miraway.mss.web.rest.responses.APIResponse;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@AuthorizedFeignClient(name = "uaa")
public interface OrganizationRepository {
    @GetMapping(value = "/api/internal/organizations/{id}")
    APIResponse<OrganizationDTO> getOrganizationById(@PathVariable @DatabaseIdConstraint String id)
        throws ResourceNotFoundException, ForbiddenException;

    @GetMapping(value = "/api/internal/organizations/getChildrenIdsByIds")
    APIResponse<Set<String>> getChildrenIdsByIds(@RequestParam @DatabaseIdListConstraint Set<String> ids);

    @GetMapping(value = "/api/internal/organizations/getOrganizationsByIds")
    APIResponse<Set<OrganizationDTO>> getOrganizationsByIds(@RequestParam @DatabaseIdListConstraint Set<String> ids);
}
