package com.javamentor.developer.social.platform.dao.impl.dto.page.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getSingleChatDtoByChatName")
public class PaginationGetSingleChatByNameDaoImpl implements PaginationDao<ChatDto> {

    private static Long userId;

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<? extends ChatDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        userId = (Long) parameters.get("userPrincipalId");
        String search = (String) parameters.get("search");

        return (List<ChatDto>) em.createQuery("select " +
                "(select max(me) from SingleChat si  join si.messages me where si.id=single.id)," +
                " single.userOne.userId," +
                "single.userTwo.userId," +
                "single.userOne.firstName," +
                "single.userOne.lastName," +
                "single.userOne.avatar," +
                "single.userOne.active.name," +
                "single.userTwo.firstName," +
                "single.userTwo.lastName," +
                "single.userTwo.avatar," +
                "single.userTwo.active.name, " +
                "single.id " +
                "from SingleChat single " +
                "WHERE (single.userOne.userId=:id OR single.userTwo.userId=:id) and " +
                "((concat(lower(single.userTwo.firstName),' ', lower(single.userTwo.lastName)) LIKE lower(:search)) or " +
                "(concat(lower(single.userTwo.lastName),' ', lower(single.userTwo.firstName)) LIKE lower(:search)))")
                .setParameter("id", userId)
                .setParameter("search", search)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(new ChatDtoResultTransformer())
                .getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return em.createQuery(
                "select COUNT(single) from SingleChat single " +
                        "WHERE (single.userOne.userId=:id OR single.userTwo.userId=:id) and " +
                        "((concat(lower(single.userTwo.firstName),' ', lower(single.userTwo.lastName)) LIKE lower(:search)) or " +
                        "(concat(lower(single.userTwo.lastName),' ', lower(single.userTwo.firstName)) LIKE lower(:search)))", Long.class)
                .setParameter("id", parameters.get("userPrincipalId"))
                .setParameter("search", parameters.get("search"))
                .getSingleResult();
    }

    private static class ChatDtoResultTransformer implements ResultTransformer {
        @Override
        public Object transformTuple(Object[] objects, String[] strings) {
            ChatDto chatDto;
            if (((Number) objects[1]).longValue() != userId) {
                chatDto = new ChatDto().builder()
                        .id(((Number) objects[11]).longValue())
                        .title(objects[3] + " " + objects[4])
                        .image((String) objects[5])
                        .active((String) objects[6])
                        .lastMessage(((Message) objects[0]).getMessage())
                        .type("singleChats")
                        .build();
            } else {
                chatDto = new ChatDto().builder()
                        .id(((Number) objects[11]).longValue())
                        .title(objects[7] + " " + objects[8])
                        .image((String) objects[9])
                        .active((String) objects[10])
                        .lastMessage(((Message) objects[0]).getMessage())
                        .type("singleChats")
                        .build();
            }
            return chatDto;
        }

        @Override
        public List transformList(List list) {
            return list;
        }

    }
}
