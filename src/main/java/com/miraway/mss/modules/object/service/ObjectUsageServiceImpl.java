package com.miraway.mss.modules.object.service;

import com.miraway.mss.modules.object.entity.ObjectUsage;
import com.miraway.mss.modules.object.repository.ObjectUsageRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ObjectUsageServiceImpl implements ObjectUsageService{

    private final ObjectUsageRepository objectUsageRepository;

    public ObjectUsageServiceImpl(ObjectUsageRepository objectUsageRepository) {
        this.objectUsageRepository = objectUsageRepository;
    }

    @Override
    public List<ObjectUsage> getByObjectId(String id){
        return objectUsageRepository.getByObjectId(Collections.singleton(new ObjectId(id)));
    }
}
