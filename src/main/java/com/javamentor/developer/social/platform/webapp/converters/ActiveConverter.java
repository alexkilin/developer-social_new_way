package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.ActiveDto;
import org.springframework.stereotype.Service;

@Service
public class ActiveConverter {

    public final ActiveDto toDto(Active active) {
        return ActiveDto.builder().name(active.getName()).build();
    }
}