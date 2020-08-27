package com.javamentor.developer.social.platform.service.abstracts.model.media;

import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.List;

public interface ImageService extends GenericService<Image, Long> {

   List<Image> getAllByUserId(Long id);

}
