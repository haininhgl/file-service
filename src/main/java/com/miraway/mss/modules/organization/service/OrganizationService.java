package com.miraway.mss.modules.organization.service;

import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.InternalServerException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.organization.dto.OrganizationDTO;
import java.util.Set;

public interface OrganizationService {

    Set<String> getChildrenIdsByIds(Set<String> idList) throws InternalServerException;

    OrganizationDTO getOrganizationById(String id) throws ResourceNotFoundException, InternalServerException, ForbiddenException;

    Set<OrganizationDTO> getOrganizationsByIds(Set<String> idList) throws InternalServerException;

    boolean isRootOrganization(OrganizationDTO organization);
}
