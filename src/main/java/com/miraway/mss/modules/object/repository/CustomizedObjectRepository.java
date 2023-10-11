package com.miraway.mss.modules.object.repository;

import com.miraway.mss.modules.object.dto.filter.ObjectFilter;
import com.miraway.mss.modules.object.entity.Object;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomizedObjectRepository {

    Page<Object> getObjectList(ObjectFilter filter, Pageable pageable);

    void updateIsDeleted(Set<String> objectIdDeletable);
}
