package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.ImageDto;

import java.util.List;

public interface ImageDTODAO {

    List<ImageDto> getAllByUserId(Long id);

}
