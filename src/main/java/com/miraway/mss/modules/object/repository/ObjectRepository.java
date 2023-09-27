package com.miraway.mss.modules.object.repository;

import com.miraway.mss.modules.object.entity.Object;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository extends MongoRepository<Object, String> {
}
