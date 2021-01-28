package com.javamentor.developer.social.platform.dao.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class GroupDaoImpl extends GenericDaoAbstract<Group, Long> implements GroupDao {

    @Override
    public void updateInfo(Group group) {
        entityManager.createQuery("UPDATE Group g " +
                "SET g.name = :name, " +
                    "g.linkSite = :linkSite, " +
                    "g.groupCategory = :groupCategory, " +
                    "g.description = :description, " +
                    "g.addressImageGroup = :addressImageGroup, " +
                    "g.lastRedactionDate = :lastRedactionDate " +
                "WHERE g.id = :id")
                .setParameter("name", group.getName())
                .setParameter("linkSite", group.getLinkSite())
                .setParameter("groupCategory", group.getGroupCategory())
                .setParameter("description", group.getDescription())
                .setParameter("addressImageGroup", group.getAddressImageGroup())
                .setParameter("lastRedactionDate", LocalDateTime.now())
                .setParameter("id", group.getId())
        .executeUpdate();
    }
}