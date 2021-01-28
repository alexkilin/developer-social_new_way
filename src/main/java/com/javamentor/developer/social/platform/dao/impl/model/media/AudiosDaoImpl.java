package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.AudiosDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class AudiosDaoImpl extends GenericDaoAbstract<Audios, Long> implements AudiosDao {

    @Override
    public Optional<Audios> getByIdWithMedia(Long id) {
        TypedQuery<Audios> query = entityManager.createQuery("SELECT a " +
                        "FROM Audios AS a " +
                        "JOIN FETCH a.media " +
                        "WHERE a.id = :paramId", Audios.class)
                .setParameter("paramId", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
