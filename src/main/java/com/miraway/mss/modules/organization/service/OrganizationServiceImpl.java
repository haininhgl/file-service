package com.miraway.mss.modules.organization.service;

import com.miraway.mss.constants.Constants;
import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.InternalServerException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.organization.dto.OrganizationDTO;
import com.miraway.mss.modules.organization.repository.OrganizationRepository;
import com.miraway.mss.web.rest.responses.APIResponse;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Set<String> getChildrenIdsByIds(Set<String> idList) throws InternalServerException {
        try {
            APIResponse<Set<String>> response = organizationRepository.getChildrenIdsByIds(idList);
            if (response == null || CollectionUtils.isEmpty(response.getData())) {
                return new HashSet<>();
            }

            return response.getData();
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }

    @Override
    public OrganizationDTO getOrganizationById(String id) throws ResourceNotFoundException, InternalServerException, ForbiddenException {
        APIResponse<OrganizationDTO> response;
        try {
            response = organizationRepository.getOrganizationById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng.");
        } catch (ForbiddenException e) {
            throw new ForbiddenException();
        } catch (Exception e) {
            throw new InternalServerException();
        }

        if (response == null || response.getData() == null) {
            throw new ResourceNotFoundException("Không tìm thấy tổ chức.");
        }

        return response.getData();
    }

    @Override
    public Set<OrganizationDTO> getOrganizationsByIds(Set<String> ids) throws InternalServerException {
        try {
            APIResponse<Set<OrganizationDTO>> response = organizationRepository.getOrganizationsByIds(ids);
            if (response == null || CollectionUtils.isEmpty(response.getData())) {
                return new HashSet<>();
            }

            return response.getData();
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }

    @Override
    public boolean isRootOrganization(OrganizationDTO organization) {
        return organization.getName().equalsIgnoreCase(Constants.ROOT_ORGANIZATION_NAME);
    }
}
