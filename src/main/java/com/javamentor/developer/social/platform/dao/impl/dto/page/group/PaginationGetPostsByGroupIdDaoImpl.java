package com.javamentor.developer.social.platform.dao.impl.dto.page.group;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component("showGroupWall")
public class PaginationGetPostsByGroupIdDaoImpl implements PaginationDao<GroupWallDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final GroupDtoDao groupDtoDao;
    private final PostDtoDao postDtoDao;

    @Autowired
    public PaginationGetPostsByGroupIdDaoImpl(GroupDtoDao groupDtoDao, PostDtoDao postDtoDao) {
        this.groupDtoDao = groupDtoDao;
        this.postDtoDao = postDtoDao;
    }

    @Override
    public List<GroupWallDto> getItems(Map<String, Object> parameters) {
        Long groupId = (Long) parameters.get("groupId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        List<GroupWallDto> groupWallDtoList = entityManager.createQuery(
                "SELECT " +
                        "p.id, " +
                        "p.lastRedactionDate, " +
                        "p.persistDate, " +
                        "p.title, " +
                        "p.text, " +
                        "(SELECT COUNT(pc) FROM PostComment pc WHERE p.id = pc.post.id), " +
                        "(SELECT COUNT(pl) FROM PostLike pl WHERE p.id = pl.post.id), " +
                        "(SELECT COUNT(bm) FROM Bookmark bm WHERE bm.post.id = p.id), " +
                        "(SELECT COUNT(rp) FROM p.reposts rp) " +
                        "FROM Group g " +
                        "LEFT JOIN g.posts p " +
                        "WHERE g.id = :groupId")
                .setParameter("groupId", groupId)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(currentPage * itemsOnPage)
                .unwrap(Query.class).setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {

                        return GroupWallDto.builder()
                                .countComments((Long) objects[5])
                                .id((Long) objects[0])
                                .lastRedactionDate((LocalDateTime) objects[1])
                                .persistDate((LocalDateTime) objects[2])
                                .text((String) objects[4])
                                .title((String) objects[3])
                                .countLikes((Long) objects[6])
                                .countBookmarks((Long) objects[7])
                                .countReposts((Long) objects[8])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                }).getResultList();
        return groupWallDtoList;
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (p) from Group g " +
                        "left join g.posts p " +
                        "where g.id = :groupId",
                Long.class
        ).setParameter("groupId", parameters.get("groupId"))
                .getSingleResult();
    }
}
