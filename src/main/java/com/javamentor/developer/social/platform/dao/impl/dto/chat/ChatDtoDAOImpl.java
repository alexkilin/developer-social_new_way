package com.javamentor.developer.social.platform.dao.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.ChatDtoDAO;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class ChatDtoDAOImpl implements ChatDtoDAO {

    public static Long userId;

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public List<ChatDto> getAllChatDtoByUserId(Long userId) {
        ChatDtoDAOImpl.userId = userId;
        List<ChatDto> chatDtoList = (List<ChatDto>) em.createQuery("select " +
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
                "from User user join user.singleChat single where user.userId=:id")
                .setParameter("id", userId)
                .unwrap(Query.class)
                .setResultTransformer(new ChatDtoResultTransformer())
                .getResultList();
        chatDtoList.addAll((List<ChatDto>) em.createQuery("select " +
                "(select max(me) from GroupChat si  join si.messages me where si.id=groupchats.id), " +
                "groupchats.image," +
                "groupchats.title," +
                "groupchats.id " +
                "from User user join user.groupChats groupchats where user.userId=:id")
                .setParameter("id", userId)
                .unwrap(Query.class)
                .setResultTransformer(new GroupChatDtoResultTransformer())
                .getResultList());
        return chatDtoList;
    }

    @Override
    public ChatDto getChatDtoByGroupChatId(Long chatId) {
        ChatDto chatDto = (ChatDto) em.createQuery("select " +
                "(select max(me) from gc.messages me), " +
                "gc.image," +
                "gc.title," +
                "gc.id " +
                "from GroupChat gc where gc.id=:id")
                .setParameter("id", chatId)
                .unwrap(Query.class)
                .setResultTransformer(new GroupChatDtoResultTransformer())
                .getSingleResult();
        return chatDto;
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
