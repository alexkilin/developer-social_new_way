package com.javamentor.developer.social.platform.webapp.converters;
import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;



@Mapper(componentModel = "spring")
public class ImageConverter {



    public ImageDTO getDTO(Image image) {

        return new ImageDTO(image.getId(), image.getMedia().getUrl(),
                image.getDescription(), image.getMedia().getPersistDateTime());

    }



}
