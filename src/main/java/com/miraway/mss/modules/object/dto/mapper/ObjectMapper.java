package com.miraway.mss.modules.object.dto.mapper;

import com.miraway.mss.modules.common.mapper.EntityMapper;
import com.miraway.mss.modules.object.dto.ObjectDTO;
import com.miraway.mss.modules.object.entity.Object;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjectMapper extends EntityMapper<ObjectDTO, Object> {
}
