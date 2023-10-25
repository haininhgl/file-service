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
    //    @Query("{ 'displayName' : ?0 }")
    //    boolean existsByDisplayName(String displayName);
    //
    //
    //    @Query("{ 'displayName' : ?0 , 'parentId' : ?1 }")
    //    boolean existsByDisplayNameAnAndParentId(String displayName, String parentId);

}
