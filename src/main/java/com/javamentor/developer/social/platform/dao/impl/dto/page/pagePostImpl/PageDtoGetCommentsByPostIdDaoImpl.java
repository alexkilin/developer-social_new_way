package com.javamentor.developer.social.platform.dao.impl.dto.page.pagePostImpl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("showPostComments")
public class PageDtoGetCommentsByPostIdDaoImpl implements PageDtoDao<CommentDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final PostDtoDao postDtoDao;

    @Autowired
    public PageDtoGetCommentsByPostIdDaoImpl(PostDtoDao postDtoDao) {
        this.postDtoDao = postDtoDao;
    }

    @Override
    public List<CommentDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        Long postId = (Long) parameters.get("postId");
        return postDtoDao.getCommentsByPostId(postId, currentPage, itemsOnPage);
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (pc) " +
                        "FROM Post p " +
                        "LEFT JOIN PostComment pc on p.id = pc.post.id " +
                        "LEFT JOIN Comment c on pc.comment.id = c.id " +
                        "WHERE p.id = :postId",
                Long.class
        ).setParameter("postId", parameters.get("postId"))
                .getSingleResult();
    }
}
