package com.javamentor.developer.social.platform.dao.impl.dto.page.post;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component("showPostComments")
public class PaginationGetCommentsByPostIdDaoImpl implements PaginationDao<CommentDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetCommentsByPostIdDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CommentDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        Long postId = (Long) parameters.get("postId");
        return entityManager.createQuery(
                "SELECT " +
                        "c.id, " +
                        "c.persistDate, " +
                        "c.lastRedactionDate, " +
                        "c.comment, " +//3
                        "(SELECT u.lastName FROM User u WHERE c.user.userId = u.userId), " +
                        "(SELECT u.firstName FROM User u WHERE c.user.userId = u.userId), " +
                        "(SELECT u.userId FROM User u WHERE c.user.userId = u.userId)," +
                        "(SELECT u.avatar FROM User u WHERE c.user.userId = u.userId)" +
                        "FROM Post p " +
                        "LEFT JOIN PostComment pc on p.id = pc.post.id " +
                        "LEFT JOIN Comment c on pc.comment.id = c.id " +
                        "WHERE p.id = :postId " +
                        "ORDER BY c.id ASC")
                .setParameter("postId", postId)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        UserDto userDto = UserDto.builder()
                                .userId((Long) objects[6])
                                .firstName((String) objects[5])
                                .lastName((String) objects[4])
                                .avatar((String) objects[7])
                                .build();

                        return CommentDto.builder()
                                .userDto(userDto)
                                .persistDate((LocalDateTime) objects[1])
                                .lastRedactionDate((LocalDateTime) objects[2])
                                .id((Long) objects[0])
                                .comment((String) objects[3])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        Map<Long, CommentDto> result = new TreeMap<>();
                        for (Object obj : list) {
                            CommentDto commentDto = (CommentDto) obj;
                            if (commentDto.getId() != null) {
                                result.put(commentDto.getId(), commentDto);
                            }
                        }
                        return new ArrayList<>(result.values());
                    }
                }).getResultList();
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
