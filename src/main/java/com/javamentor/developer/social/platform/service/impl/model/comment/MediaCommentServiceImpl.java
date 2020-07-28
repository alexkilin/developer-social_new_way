package com.javamentor.developer.social.platform.service.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.comment.MediaCommentDAO;
import com.javamentor.developer.social.platform.models.entity.comment.MediaComment;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.MediaCommentService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaCommentServiceImpl extends GenericServiceAbstract<MediaComment, Long> implements MediaCommentService {

    @Autowired
    public MediaCommentServiceImpl(MediaCommentDAO dao) {
        super(dao);
    }
}
