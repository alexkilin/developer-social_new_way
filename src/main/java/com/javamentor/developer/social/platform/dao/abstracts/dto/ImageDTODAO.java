package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import com.javamentor.developer.social.platform.models.entity.media.Image;

import java.util.List;

public interface ImageDTODAO {

    List<ImageDTO> getAllByUserId(Long id);

}
