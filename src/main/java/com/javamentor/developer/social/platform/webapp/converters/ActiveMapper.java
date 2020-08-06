package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.ActiveDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActiveMapper {

    ActiveMapper INSTANCE = Mappers.getMapper(ActiveMapper.class);

    ActiveDto toDto(Active active);

    Active toEntity(ActiveDto activeDto);
}
