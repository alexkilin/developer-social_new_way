package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumImageDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumConverter;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AlbumImageDtoDaoImpl implements AlbumImageDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    private final AlbumAudioService albumAudioService;
    private final AlbumService albumService;
    private final UserService userService;
    private final AlbumConverter albumConverter;

    @Autowired
    public AlbumImageDtoDaoImpl(AlbumAudioService albumAudioService, AlbumService albumService, UserService userService, AlbumConverter albumConverter) {
        this.albumAudioService = albumAudioService;
        this.albumService = albumService;
        this.userService = userService;
        this.albumConverter = albumConverter;
    }

    @Override
    public List<AlbumImageDto> getAllByUserId(Long userId) {
        return entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto(" +
                        "a.id, " +
                        "a.name, " +
                        "a.icon," +
                        "a.persistDate," +
                        "a.lastRedactionDate) " +
                        "FROM Album a " +
                        "WHERE a.mediaType = :type " +
                        "AND a.userOwnerId.userId = :id " +
                        "ORDER BY a.id ASC", AlbumImageDto.class)
                .setParameter("type", MediaType.IMAGE)
                .setParameter("id", userId)
                .getResultList();

    }
}
