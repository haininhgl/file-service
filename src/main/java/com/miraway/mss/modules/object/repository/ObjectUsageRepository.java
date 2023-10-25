package com.miraway.mss.modules.object.repository;

import com.miraway.mss.modules.object.entity.ObjectUsage;
import java.util.List;
import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectUsageRepository extends MongoRepository<ObjectUsage, String> {
    @Query("{ 'object.$id' : { $in : ?0 }, 'isDeletable' : true }")
    List<ObjectUsage> getByObjectId(Set<ObjectId> ids);
}
