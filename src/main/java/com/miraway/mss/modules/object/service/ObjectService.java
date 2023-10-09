package com.miraway.mss.modules.object.service;

import com.miraway.mss.modules.common.exception.BadRequestException;
import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.object.dto.filter.ObjectFilter;
import com.miraway.mss.modules.object.entity.Object;
import com.miraway.mss.web.rest.request.MoveFileRequest;
import com.miraway.mss.web.rest.request.ObjectRequest;
import com.miraway.mss.web.rest.request.RenameObjectRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ObjectService {
    Page<Object> getObjectList(ObjectFilter filter, Pageable pageable) throws ResourceNotFoundException, ForbiddenException;

    Object createFolder(ObjectRequest request) throws BadRequestException;

    Object updateById(String id, RenameObjectRequest request) throws ForbiddenException, ResourceNotFoundException, BadRequestException;

    List<Object> updateFile(List<MoveFileRequest> requests) throws ResourceNotFoundException;

    List<String> softDelete(Set<String> ids) throws ResourceNotFoundException, ForbiddenException;
}
