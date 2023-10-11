package com.miraway.mss.modules.object.repository;

import com.miraway.mss.modules.object.entity.ObjectUsage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ObjectUsageRepository extends MongoRepository<ObjectUsage, String> {

    @Query("{'object.$id': { $in: ?0 }}")
    List<ObjectUsage> getByObjectId(Set<ObjectId> ids);
}
