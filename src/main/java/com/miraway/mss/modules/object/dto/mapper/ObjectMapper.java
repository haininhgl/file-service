package com.miraway.mss.modules.object.dto.mapper;

import com.miraway.mss.modules.common.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjectMapper extends EntityMapper<ObjectMapper, Object> {
}
