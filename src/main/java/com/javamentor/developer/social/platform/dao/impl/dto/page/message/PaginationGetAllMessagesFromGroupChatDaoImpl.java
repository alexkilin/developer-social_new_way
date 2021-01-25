package com.javamentor.developer.social.platform.dao.impl.dto.page.message;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("getAllMessagesFromGroupChat")
public class PaginationGetAllMessagesFromGroupChatDaoImpl implements PaginationDao<MessageDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAllMessagesFromGroupChatDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MessageDto> getItems(Map<String, Object> parameters) {
        Long chatId = (Long) parameters.get("chatId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        return entityManager.createQuery("select " +
                "m.id, " +
                "m.lastRedactionDate," +
                "m.persistDate, " +
                "m.userSender.avatar, " +
                "m.message " +
                "from GroupChat gr join gr.chat.messages m where gr.id = :chatId")
                .setParameter("chatId", chatId)
                .unwrap(Query.class)
                .setFirstResult((currentPage - 1) * currentPage)
                .setMaxResults(itemsOnPage)
                .setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                            return MessageDto.builder()
                                    .id((Long) objects[0])
                                    .lastRedactionDate((LocalDateTime) objects[1])
                                    .persistDate((LocalDateTime) objects[2])
                                    .userSenderImage((String) objects[3])
                                    .message((String) objects[4])
                                    .build();

                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                }).getResultList();
    }

        @Override
        public Long getCount(Map < String, Object > parameters){
            return entityManager.createQuery(
                    "select count (m) FROM GroupChat gr join gr.chat.messages m where gr.id = :chatId ",
                    Long.class
            ).setParameter("chatId", parameters.get("chatId"))
                    .getSingleResult();
        }
    }
