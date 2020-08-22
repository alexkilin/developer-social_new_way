package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserDAO;
import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public abstract class ImageConverter {

    private final UserDAO  userDAO;
    private final AlbumDAO albumDAO;

    @Autowired
    protected ImageConverter(UserDAO dao, AlbumDAO albumDAO) {
        userDAO = dao;
        this.albumDAO = albumDAO;
    }

    public ImageDTO getDTO(Image image) {
        return new ImageDTO(image.getId(),
                image.getMedia().getUrl(),
                image.getDescription(),
                image.getMedia().getPersistDateTime());
    }

    public Image getImage(ImageDTO DTO, Long userId, String url, Long albumId) {

        return new Image(
                new Media(userDAO.getById(userId),
                url,
                LocalDateTime.now(),
                albumDAO.getById(albumId)),
                DTO.getDescription());
    }

}
