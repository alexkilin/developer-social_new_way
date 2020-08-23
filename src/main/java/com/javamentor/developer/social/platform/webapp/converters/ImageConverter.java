package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.dao.impl.model.album.AlbumDAOImpl;
import com.javamentor.developer.social.platform.dao.impl.model.user.UserDAOImpl;
import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public abstract class ImageConverter {



    public ImageDTO getDTO(Image image) {

        return new ImageDTO(image.getId(), image.getMedia().getUrl(),
                image.getDescription(), image.getMedia().getPersistDateTime());

    }



}
