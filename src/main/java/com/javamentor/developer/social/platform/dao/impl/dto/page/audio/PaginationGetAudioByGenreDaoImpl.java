package com.javamentor.developer.social.platform.dao.impl.dto.page.audio;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.music.GenreDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component("getAudioByGenre")
public class PaginationGetAudioByGenreDaoImpl implements PaginationDao<AudioDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAudioByGenreDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getItems(Map<String, Object> parameters) {
        String genre = (String)parameters.get("genre");
        int currentPage = (int)parameters.get("currentPage");
        int itemsOnPage = (int)parameters.get("itemsOnPage");

        return (List<AudioDto>) entityManager
                .createQuery("SELECT " +
                        "gm.audios.id, " +
                        "gm.audios.icon, " +
                        "gm.audios.name, " +
                        "gm.audios.author, " +
                        "gm.audios.media.url, " +
                        "gm.audios.media.persistDateTime, " +
                        "gm.audios.album, " +
                        "gm.audios.length, " +
                        "gm.genre.id, " +
                        "gm.genre.title, " +
                        "gm.audios.listening " +
                        "FROM GenreMusic gm " +
                        "WHERE gm.genre.title = :genre")
                .setParameter("genre", genre)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(
                                    Object[] objects, String[] strings) {

                                GenreDto genreDto = GenreDto.builder()
                                        .id((Long)objects[8])
                                        .title((String) objects[9])
                                        .build();
                                return AudioDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .icon((String) objects[1])
                                        .name((String) objects[2])
                                        .author((String) objects[3])
                                        .url((String) objects[4])
                                        .persistDateTime((LocalDateTime) objects[5])
                                        .album((String) objects[6])
                                        .length((Integer) objects[7])
                                        .genreDto(genreDto)
                                        .listening((Integer) objects[10])
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "SELECT count(gm.audios)" +
                        "FROM GenreMusic gm " +
                        "WHERE gm.genre.title = :genre",
                Long.class
        ).setParameter("genre", parameters.get("genre"))
                .getSingleResult();
    }
}