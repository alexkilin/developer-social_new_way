package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.StatusDto;
import org.springframework.stereotype.Service;

@Service
public class StatusConverter {

    public final StatusDto toDto(Status status) {
        return StatusDto.builder().name(status.getName()).build();
    }
}
