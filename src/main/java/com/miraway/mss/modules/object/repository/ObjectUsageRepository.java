package com.miraway.mss.modules.object.repository;

import com.miraway.mss.modules.object.entity.ObjectUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectUsageRepository extends MongoRepository<ObjectUsage, String> {
}
