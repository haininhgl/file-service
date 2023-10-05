package com.miraway.mss.modules.object.service;

import com.miraway.mss.modules.object.entity.ObjectUsage;

import java.util.List;

public interface ObjectUsageService {

    List<ObjectUsage> getByObjectId(String id);
}
