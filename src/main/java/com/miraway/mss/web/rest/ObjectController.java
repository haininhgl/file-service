package com.miraway.mss.web.rest;

import static com.miraway.mss.constants.Constants.DEFAULT_SORT_BY;
import static com.miraway.mss.constants.Constants.DEFAULT_SORT_DIRECTION;

import com.miraway.mss.config.ApplicationProperties;
import com.miraway.mss.modules.common.exception.BadRequestException;
import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.InternalServerException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.common.validator.DatabaseIdConstraint;
import com.miraway.mss.modules.common.validator.DatabaseIdListConstraint;
import com.miraway.mss.modules.object.dto.ObjectDTO;
import com.miraway.mss.modules.object.dto.filter.ObjectFilter;
import com.miraway.mss.modules.object.dto.mapper.ObjectMapper;
import com.miraway.mss.modules.object.entity.Object;
import com.miraway.mss.modules.object.service.ObjectService;
import com.miraway.mss.web.rest.request.MoveFileRequest;
import com.miraway.mss.web.rest.request.ObjectRequest;
import com.miraway.mss.web.rest.request.PaginationRequest;
import com.miraway.mss.web.rest.request.RenameObjectRequest;
import com.miraway.mss.web.rest.responses.APIResponse;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class ObjectController {

    private final ApplicationProperties applicationProperties;

    private final ObjectService objectService;

    private final ObjectMapper objectMapper;

    public ObjectController(ApplicationProperties applicationProperties, ObjectService objectService, ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.objectService = objectService;
        this.objectMapper = objectMapper;
    }

    @PreAuthorize("@mssGuard.hasAnyPermissions(authentication, 'storage.storage:create')")
    @PostMapping("/objects")
    public APIResponse<ObjectDTO> createFolder(@Valid @RequestBody ObjectRequest request)
        throws BadRequestException, ForbiddenException, InternalServerException, ResourceNotFoundException {
        Object object = new Object();
        object.setDisplayName(request.getDisplayName());
        object.setParentId(request.getParentId());
        object.setOrganizationId(request.getOrganizationId());

        object = objectService.createFolder(object);
        ObjectDTO objectDTO = objectMapper.toDto(object);
        return APIResponse.newSuccessResponse(objectDTO);
    }

    @PreAuthorize("@mssGuard.hasAnyPermissions(authentication, 'storage.storage:read')")
    @GetMapping("/objects")
    public APIResponse<List<ObjectDTO>> getObjectList(@Valid ObjectFilter filter, PaginationRequest paginationRequest)
        throws ResourceNotFoundException, ForbiddenException, InternalServerException {
        if (StringUtils.isBlank(paginationRequest.getSortBy())) {
            paginationRequest.setSortBy(DEFAULT_SORT_BY);
            paginationRequest.setSortDirection(DEFAULT_SORT_DIRECTION);
        }

        Pageable pageable = paginationRequest.toPageable(applicationProperties.getMaxPageSize());

        Page<Object> objectPage = objectService.getObjectList(filter, pageable);
        Page<ObjectDTO> result = objectPage.map(objectMapper::toDto);
        return APIResponse.newSuccessPageResponse(result);
    }

    @PreAuthorize("@mssGuard.hasAnyPermissions(authentication, 'storage.storage:update')")
    @PutMapping("/objects/{id}")
    public APIResponse<Object> renameObject(@PathVariable @DatabaseIdConstraint String id, @Valid @RequestBody RenameObjectRequest request)
        throws ForbiddenException, ResourceNotFoundException, BadRequestException {
        Object object = objectService.getById(id);
        object.setDisplayName(request.getDisplayName());
        object = objectService.updateById(id, object);
        return APIResponse.newSuccessResponse(object);
    }

    @PreAuthorize("@mssGuard.hasAnyPermissions(authentication, 'storage.storage:update')")
    @PutMapping("/moveObjects")
    public APIResponse<List<Object>> moveObject(@Valid @RequestBody MoveFileRequest request)
        throws ResourceNotFoundException, BadRequestException {
        objectService.updateFile(request);
        return APIResponse.newSuccessResponse();
    }

    @PreAuthorize("@mssGuard.hasAnyPermissions(authentication, 'storage.storage:update')")
    @DeleteMapping("/objects")
    public APIResponse<List<String>> deleteObjectById(@DatabaseIdListConstraint @RequestParam(value = "ids") Set<String> ids)
        throws ResourceNotFoundException, BadRequestException {
        objectService.softDelete(ids);
        return APIResponse.newSuccessResponse();
    }
}
