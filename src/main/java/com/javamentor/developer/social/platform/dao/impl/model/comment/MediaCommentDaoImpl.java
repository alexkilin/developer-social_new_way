package com.javamentor.developer.social.platform.dao.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.model.comment.MediaCommentDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.comment.MediaComment;
import org.springframework.stereotype.Repository;

@Repository
public class MediaCommentDaoImpl extends GenericDaoAbstract<MediaComment, Long> implements MediaCommentDao {
}
