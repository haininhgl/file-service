package com.miraway.mss.modules.object.service;

import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.object.entity.ObjectUsage;
import com.miraway.mss.web.rest.request.ObjectUsageRequest;

import java.util.List;
import java.util.Set;

public interface ObjectUsageService {

    List<ObjectUsage> getByObjectId(Set<String> ids);

    ObjectUsage createObjectUsage(ObjectUsageRequest request) throws ResourceNotFoundException;
}
