package com.miraway.mss.web.rest;

import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.object.dto.ObjectUsageDTO;
import com.miraway.mss.modules.object.dto.mapper.ObjectUsageMapper;
import com.miraway.mss.modules.object.entity.ObjectUsage;
import com.miraway.mss.modules.object.service.ObjectUsageService;
import com.miraway.mss.web.rest.request.ObjectUsageRequest;
import com.miraway.mss.web.rest.responses.APIResponse;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
public class ObjectUsageController {

    private final ObjectUsageService objectUsageService;

    private final ObjectUsageMapper objectUsageMapper;

    public ObjectUsageController(ObjectUsageService objectUsageService, ObjectUsageMapper objectUsageMapper) {
        this.objectUsageService = objectUsageService;
        this.objectUsageMapper = objectUsageMapper;
    }

    // API for testing
    @PostMapping("/objectUsages")
    public APIResponse<ObjectUsageDTO> createObjectUsage(@Valid @RequestBody ObjectUsageRequest request) throws ResourceNotFoundException {
        ObjectUsage objectUsage = objectUsageService.createObjectUsage(request);
        ObjectUsageDTO objectUsageDTO = objectUsageMapper.toDto(objectUsage);
        return APIResponse.newSuccessResponse(objectUsageDTO);
    }
}
