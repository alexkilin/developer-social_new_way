package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.ImageDto;

import java.util.List;

public interface ImageDTOService {

    List<ImageDto> getAllByUserId(Long id);

}
