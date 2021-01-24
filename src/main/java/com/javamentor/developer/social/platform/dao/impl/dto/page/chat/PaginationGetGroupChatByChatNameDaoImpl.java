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

@Component("getGroupChatDtoByChatName")
public class PaginationGetGroupChatByChatNameDaoImpl implements PaginationDao<ChatDto> {

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
        int countSingleChat = (int) parameters.get("countSingleChat");
        int itemsOnLastPageSingleChat = (int) parameters.get("itemsOnLastPageSingleChat");
        int singlePage = (int) parameters.get("singlePage");

        return em.createQuery("select " +
                "(select max(me) from GroupChat si join si.messages me where si.id=groupchats.id), " +
                "groupchats.chat.image," +
                "groupchats.chat.title," +
                "groupchats.id " +
                "from User user join user.groupChats groupchats where user.userId=:id and (lower(groupchats.chat.title) LIKE lower(:search) or lower(groupchats.chat.title) LIKE lower(:searchMiddle)) order by groupchats.id ASC")
                .setParameter("id", userId)
                .setParameter("search", search)
                .setParameter("searchMiddle", "% " + search)
                .setFirstResult((currentPage - singlePage - 1) * itemsOnPage - itemsOnLastPageSingleChat)
                .setMaxResults(itemsOnPage - countSingleChat)
                .unwrap(Query.class)
                .setResultTransformer(new PaginationGetGroupChatByChatNameDaoImpl.GroupChatDtoResultTransformer())
                .getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return em.createQuery(
                "select COUNT(user) from User user join user.groupChats groupchats " +
                        "WHERE user.userId=:id and (lower(groupchats.chat.title) LIKE lower(:search) or lower(groupchats.chat.title) LIKE lower(:searchMiddle))", Long.class)
                .setParameter("id", parameters.get("userPrincipalId"))
                .setParameter("search", parameters.get("search"))
                .setParameter("searchMiddle", "% " + parameters.get("search"))
                .getSingleResult();

    }

    private static class GroupChatDtoResultTransformer implements ResultTransformer {

        @Override
        public Object transformTuple(Object[] objects, String[] strings) {
            return new ChatDto().builder()
                    .id(((Number) objects[3]).longValue())
                    .image((String) objects[1])
                    .title((String) objects[2])
                    .lastMessage(((Message) objects[0]).getMessage())
                    .type("groupChats")
                    .build();
        }

        @Override
        public List transformList(List list) {
            return list;
        }
    }
}
