package com.javamentor.developer.social.platform.dao.impl.dto.page.pagePalylistImpl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PlaylistDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component("getPlaylistsOfUser")
public class PageDtoGetByUserIdDaoImpl implements PageDtoDao<PlaylistGetDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final PlaylistDtoDao playlistDtoDao;

    @Autowired
    public PageDtoGetByUserIdDaoImpl(PlaylistDtoDao playlistDtoDao) {
        this.playlistDtoDao = playlistDtoDao;
    }

    @Override
    public List<PlaylistGetDto> getItems(Map<String, Object> parameters) {
        Long userId = (Long) parameters.get("userId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        List<PlaylistGetDto> list = entityManager.createQuery("SELECT " +
                "p.id," +
                "p.name, " +
                "p.image," +
                "p.ownerUser.userId," +
                "p.persistDateTime " +
                "FROM Playlist as p " +
                "LEFT JOIN p.playlistContent as c " +
                "WHERE p.ownerUser.userId = :userId")
                .setParameter("userId", userId)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(currentPage * itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {

                        return PlaylistGetDto.builder()
                                .id((Long) objects[0])
                                .name((String) objects[1])
                                .image((String) objects[2])
                                .ownerUserId((Long) objects[3])
                                .persistDateTime((LocalDateTime) objects[4])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();

        list.forEach(playlistGetDto -> {
            playlistGetDto.setContent(playlistDtoDao.getAudioDtoByPlaylistId(playlistGetDto.getId()));
        });
        return list;
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (p) from Playlist p where p.id = :playlistId ",
                Long.class
        ).setParameter("playlistId", parameters.get("playlistId"))
                .getSingleResult();
    }
}

