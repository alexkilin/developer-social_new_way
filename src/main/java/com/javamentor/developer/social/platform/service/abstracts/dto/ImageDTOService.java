package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.List;

public interface ImageDTOService {

    List<ImageDTO> getAllByUserId(Long id);

}
