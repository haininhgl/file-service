package com.miraway.mss.modules.object.service;

import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.object.entity.ObjectUsage;
import com.miraway.mss.web.rest.request.ObjectUsageRequest;

import java.util.List;

public interface ObjectUsageService {

    List<ObjectUsage> getByObjectId(String id);

    ObjectUsage createObjectUsage(ObjectUsageRequest request) throws ResourceNotFoundException;
}
