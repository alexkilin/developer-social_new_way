package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioLastPlayedDto;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class AudioDtoDaoImpl implements AudioDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Audios getAudioOfId(Long id) {
        return entityManager.find(Audios.class, id);
    }

    @Override
    public Optional<AudioLastPlayedDto> getLastPlayedAudioDtoByUserId(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.media.music.AudioLastPlayedDto (a.id," +
                        " m.url, a.icon, " +
                        "a.name" +
                        ", a.author, a.album, a.length, a.listening, " +
                        " m.persistDateTime "+
                        //", a.playlistId
                        " )" +
                        " FROM Audios as a JOIN User as u ON u.audioLastPlayed.id = a.id" +
                        " JOIN Media as m ON m.id = a.id" +
                        " WHERE u.id = :id" , AudioLastPlayedDto.class)
                .setParameter("id", id));
    }
}



























