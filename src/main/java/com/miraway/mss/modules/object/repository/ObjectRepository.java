package com.miraway.mss.modules.object.repository;

import com.miraway.mss.modules.object.entity.Object;
import java.util.List;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository extends MongoRepository<Object, String>, CustomizedObjectRepository {
    boolean existsByDisplayNameIgnoreCaseAndParentId(String displayName, String updatedParentId);

    List<Object> getByIdIn(Set<String> ids);
}
