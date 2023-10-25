package com.miraway.mss.modules.object.service;

import com.miraway.mss.modules.common.exception.BadRequestException;
import com.miraway.mss.modules.common.exception.ForbiddenException;
import com.miraway.mss.modules.common.exception.InternalServerException;
import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.object.dto.filter.ObjectFilter;
import com.miraway.mss.modules.object.entity.Object;
import com.miraway.mss.web.rest.request.MoveFileRequest;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ObjectService {
    Page<Object> getObjectList(ObjectFilter filter, Pageable pageable)
        throws ResourceNotFoundException, ForbiddenException, InternalServerException;

    Object createFolder(Object object) throws BadRequestException, ForbiddenException, InternalServerException, ResourceNotFoundException;

    Object getById(String id) throws ResourceNotFoundException;

    Object updateById(String id, Object object) throws ForbiddenException, ResourceNotFoundException, BadRequestException;

    List<Object> updateFile(MoveFileRequest request) throws ResourceNotFoundException, BadRequestException;

    List<String> softDelete(Set<String> ids) throws ResourceNotFoundException, BadRequestException;
}
