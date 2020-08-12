package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.album.UserHasAlbum;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
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

    @Autowired
    public AlbumDtoDaoImpl(AlbumAudioService albumAudioService, AlbumService albumService, UserService userService) {
        this.albumAudioService = albumAudioService;
        this.albumService = albumService;
        this.userService = userService;
    }

    @Override
    public List<AlbumDto> getAlbumOfUser(Long id) {
        List<AlbumDto> albums = entityManager
                .createQuery("SELECT " +
                        "c.album.id, " +
                        "c.album.name, " +
                        "c.album.icon " +
                        "FROM UserHasAlbum as c WHERE c.user.userId = :id")
                .setParameter("id", id)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(
                                    Object[] objects, String[] strings) {
                                return AlbumDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .name((String) objects[1])
                                        .name((String) objects[2])
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return albums;
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


}
