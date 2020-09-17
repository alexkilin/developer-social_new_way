package com.javamentor.developer.social.platform.webapp.converters;
import com.javamentor.developer.social.platform.models.dto.ImageDto;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public class ImageConverter {



    public ImageDto getDTO(Image image) {

        return new ImageDto(image.getId(), image.getMedia().getUrl(),
                image.getDescription(), image.getMedia().getPersistDateTime());

    }



}
