package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.AlbumImageDTO;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public class AlbumImageConverter {
    public AlbumImageDTO getDTO(AlbumImage albumImage) {
        return new AlbumImageDTO(albumImage.getId(),
                albumImage.getAlbum().getName(),
                albumImage.getAlbum().getIcon(),
                albumImage.getAlbum().getPersistDate(),
                albumImage.getAlbum().getLastRedactionDate());
    }
}
