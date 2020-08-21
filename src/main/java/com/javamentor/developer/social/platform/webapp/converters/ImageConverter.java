package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import org.springframework.stereotype.Component;

@Component
public abstract class ImageConverter {

    public ImageDTO getDTO(Image image) {
        return new ImageDTO(image.getId(),
                image.getMedia().getUrl(),
                image.getDescription(),
                image.getMedia().getPersistDateTime());
    }

    /*public Image getImage(ImageDTO DTO, Media media) {
        return new Image(DTO.getId(), media, DTO.getDescription());
    }*/

}
