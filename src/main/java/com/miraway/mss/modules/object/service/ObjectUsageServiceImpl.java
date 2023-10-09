package com.miraway.mss.modules.object.service;

import com.miraway.mss.modules.common.exception.ResourceNotFoundException;
import com.miraway.mss.modules.object.entity.Object;
import com.miraway.mss.modules.object.entity.ObjectUsage;
import com.miraway.mss.modules.object.repository.ObjectRepository;
import com.miraway.mss.modules.object.repository.ObjectUsageRepository;
import com.miraway.mss.web.rest.request.ObjectUsageRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ObjectUsageServiceImpl implements ObjectUsageService{

    private final ObjectUsageRepository objectUsageRepository;

    private final ObjectRepository objectRepository;

    public ObjectUsageServiceImpl(ObjectUsageRepository objectUsageRepository, ObjectRepository objectRepository) {
        this.objectUsageRepository = objectUsageRepository;
        this.objectRepository = objectRepository;
    }

    @Override
    public List<ObjectUsage> getByObjectId(String id){
        return objectUsageRepository.getByObjectId(Collections.singleton(new ObjectId(id)));
    }

    @Override
    public ObjectUsage createObjectUsage(ObjectUsageRequest request) throws ResourceNotFoundException {
        Object object = objectRepository.findById(request.getObjectId())
            .orElseThrow(() -> new ResourceNotFoundException("Object not found"));

        ObjectUsage objectUsage = new ObjectUsage();
        objectUsage.setObject(object);
        BeanUtils.copyProperties(request, objectUsage);
        return objectUsageRepository.save(objectUsage);
    }
}
