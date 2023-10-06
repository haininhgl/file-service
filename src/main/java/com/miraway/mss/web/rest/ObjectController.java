package com.miraway.mss.web.rest;

import com.miraway.mss.config.ApplicationProperties;
import com.miraway.mss.modules.common.exception.BadRequestException;
import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.object.dto.ObjectDTO;
import com.miraway.mss.modules.object.dto.filter.ObjectFilter;
import com.miraway.mss.modules.object.dto.mapper.ObjectMapper;
import com.miraway.mss.modules.object.entity.Object;
import com.miraway.mss.modules.object.enumaration.ObjectType;
import com.miraway.mss.modules.object.service.ObjectService;
import com.miraway.mss.web.rest.request.MoveFileRequest;
import com.miraway.mss.web.rest.request.ObjectRequest;
import com.miraway.mss.web.rest.request.PaginationRequest;
import com.miraway.mss.web.rest.request.RenameObjectRequest;
import com.miraway.mss.web.rest.responses.APIResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.miraway.mss.constants.Constants.DEFAULT_SORT_BY;
import static com.miraway.mss.constants.Constants.DEFAULT_SORT_DIRECTION;

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

    @PostMapping("/objects")
    public APIResponse<ObjectDTO> createFolder(@Valid @RequestBody ObjectRequest request) throws BadRequestException {
        Object object = objectService.createFolder(request);
        ObjectDTO objectDTO = objectMapper.toDto(object);
        return APIResponse.newSuccessResponse(objectDTO);
    }

    @GetMapping("/objects")
    public APIResponse<List<ObjectDTO>> getObjectList(@Valid ObjectFilter filter, PaginationRequest paginationRequest) throws ResourceNotFoundException, ForbiddenException {
        if (StringUtils.isBlank(paginationRequest.getSortBy())) {
            paginationRequest.setSortBy(DEFAULT_SORT_BY);
            paginationRequest.setSortDirection(DEFAULT_SORT_DIRECTION);
        }

        Pageable pageable = paginationRequest.toPageable(applicationProperties.getMaxPageSize());

        Page<Object> objectPage = objectService.getObjectList(filter, pageable);
        Page<ObjectDTO> result = objectPage.map(objectMapper::toDto);
        return APIResponse.newSuccessPageResponse(result);
    }

    @GetMapping("/folders")
    public APIResponse<List<ObjectDTO>> getFolderList(@Valid ObjectFilter filter, PaginationRequest paginationRequest) throws ForbiddenException, ResourceNotFoundException {
        if (StringUtils.isBlank(paginationRequest.getSortBy())) {
            paginationRequest.setSortBy(DEFAULT_SORT_BY);
            paginationRequest.setSortDirection(DEFAULT_SORT_DIRECTION);
        }

        filter.setType(ObjectType.FOLDER);

        Pageable pageable = paginationRequest.toPageable(applicationProperties.getMaxPageSize());

        Page<Object> objectPage = objectService.getObjectList(filter, pageable);
        Page<ObjectDTO> folderResult = objectPage.map(objectMapper::toDto);
        return APIResponse.newSuccessPageResponse(folderResult);
    }

    @PutMapping("/objects/{id}")
    public APIResponse<Object> renameObject(@PathVariable String id, @RequestBody RenameObjectRequest request) throws ForbiddenException, ResourceNotFoundException, BadRequestException {
        Object object = objectService.updateById(id, request);
        return APIResponse.newSuccessResponse(object);
    }

    @PutMapping("/moveObjects")
    public APIResponse<List<Object>> moveObject(@RequestBody List<MoveFileRequest> requests) throws ResourceNotFoundException, IOException {
        objectService.updateFile(requests);
        return APIResponse.newSuccessResponse();
    }

    @DeleteMapping("/objects")
    public APIResponse<List<String>> deleteObjectById(@RequestParam(value = "ids") Set<String> ids) throws ResourceNotFoundException, ForbiddenException {
        List<String> deletedObjectIds = objectService.softDelete(ids);
        return APIResponse.newSuccessResponse(deletedObjectIds);
    }
}
