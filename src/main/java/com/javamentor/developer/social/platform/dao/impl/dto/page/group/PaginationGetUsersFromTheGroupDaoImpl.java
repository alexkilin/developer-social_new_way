package com.javamentor.developer.social.platform.dao.impl.dto.page.group;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component("getUsersFromTheGroup")
public class PaginationGetUsersFromTheGroupDaoImpl implements PaginationDao<UserDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetUsersFromTheGroupDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserDto> getItems(Map<String, Object> parameters) {
        Long groupId = (Long)parameters.get("groupId");
        int currentPage = (int)parameters.get("currentPage");
        int itemsOnPage = (int)parameters.get("itemsOnPage");

        Query<UserDto> queryUsersFromTheGroup = (Query<UserDto>) entityManager.createQuery(
                "SELECT " +
                        "u.userId, " +
                        "u.firstName, " +
                        "u.lastName, " +
                        "u.dateOfBirth, " +
                        "u.education, " +
                        "u.aboutMe, " +
                        "u.avatar, " +
                        "u.email, " +
                        "u.password, " +
                        "u.city, " +
                        "u.linkSite, "+
                        "u.role.name, " +
                        "u.status, " +
                        "u.active.name " +
                        "FROM User u join GroupHasUser g ON u.userId = g.user.userId " +
                        "WHERE g.group.id = :groupId " +
                        "ORDER BY u.userId ASC")
                .setParameter("groupId", groupId)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage);
        return (List<UserDto>) queryUsersFromTheGroup.unwrap(Query.class).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                return UserDto.builder()
                        .userId(((Number) objects[0]).longValue())
                        .firstName((String) objects[1])
                        .lastName((String) objects[2])
                        .dateOfBirth((LocalDate) objects[3])
                        .education((String) objects[4])
                        .aboutMe((String) objects[5])
                        .avatar((String) objects[6])
                        .email((String) objects[7])
                        .password((String) objects[8])
                        .city((String) objects[9])
                        .linkSite((String) objects[10])
                        .roleName(((String) objects[11]))
                        .status((String) objects[12])
                        .activeName((String) objects[13]).build();
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (g) from User u join GroupHasUser g on u.userId = g.user.userId where g.group.id = :groupId",
                Long.class
        ).setParameter("groupId", parameters.get("groupId"))
                .getSingleResult();
    }
}
