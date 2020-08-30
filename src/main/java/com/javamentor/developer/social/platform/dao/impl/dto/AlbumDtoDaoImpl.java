package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.album.UserHasAlbum;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumConverter;
import net.bytebuddy.implementation.bytecode.Throw;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AlbumDtoDaoImpl implements AlbumDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    private final AlbumAudioService albumAudioService;
    private final AlbumService albumService;
    private final UserService userService;
    private final AlbumConverter albumConverter;

    @Autowired
    public AlbumDtoDaoImpl(AlbumAudioService albumAudioService, AlbumService albumService, UserService userService, AlbumConverter albumConverter) {
        this.albumAudioService = albumAudioService;
        this.albumService = albumService;
        this.userService = userService;
        this.albumConverter = albumConverter;
    }

    @Override
    public List<AlbumDto> getAlbumOfUser(Long id) {
        return entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.AlbumDto(c.album.id, c.album.name, c.album.icon)" +
                        "FROM UserHasAlbum " +
                        "AS c " +
                        "WHERE c.user.userId = :id", AlbumDto.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    @Transactional
    public void createAlbum(String name, String icon, Long userId) {
        albumAudioService.create(new AlbumAudios(name, icon, userService.getById(userId)));
        UserHasAlbum userHasAlbum = new UserHasAlbum();
        Album album = entityManager.createQuery("SELECT a from Album as a WHERE a.userOwnerId.userId = :userId and a.name = :name", Album.class)
                .setParameter("userId",userId )
                .setParameter("name", name)
                .getSingleResult();
        System.out.println(album.toString());
        userHasAlbum.setAlbum(album);
        userHasAlbum.setUser(userService.getById(userId));
        entityManager.persist(userHasAlbum);
    }

    @Override
    @Transactional
    public AlbumDto createAlbum(AlbumDto albumDto, User userOwnerId) {
        AlbumAudios albumAudios = albumConverter.toAlbumAudios(albumDto, userOwnerId);
        entityManager.persist(albumAudios);
        entityManager.persist(UserHasAlbum.builder()
                .user(userOwnerId)
                .album(albumAudios.getAlbum())
                .build());
        return albumConverter.toAlbumDto(albumAudios);
    }

}
