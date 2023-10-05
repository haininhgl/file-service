package com.miraway.mss.modules.object.dto.mapper;

import com.miraway.mss.modules.common.mapper.EntityMapper;
import com.miraway.mss.modules.object.dto.ObjectUsageDTO;
import com.miraway.mss.modules.object.entity.ObjectUsage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ObjectMapper.class})
public interface ObjectUsageMapper extends EntityMapper<ObjectUsageDTO, ObjectUsage> {
}
