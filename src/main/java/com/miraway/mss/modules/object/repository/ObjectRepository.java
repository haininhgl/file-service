package com.miraway.mss.modules.object.repository;

import com.miraway.mss.modules.object.entity.Object;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectRepository extends MongoRepository<Object, String>,CustomizedObjectRepository{

    boolean existsByDisplayNameAndParentId(String displayName, String parentId);

    List<Object> findByParentId(String parentId);

}
