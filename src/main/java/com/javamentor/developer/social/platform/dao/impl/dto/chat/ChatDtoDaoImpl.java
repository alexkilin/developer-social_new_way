package com.javamentor.developer.social.platform.dao.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.ChatDtoDao;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ChatDtoDaoImpl implements ChatDtoDao {

    public static Long userId;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<ChatDto> getAllChatDtoByUserId(Long userId) {
        ChatDtoDaoImpl.userId = userId;
        List<ChatDto> chatDtoList = (List<ChatDto>) em.createQuery("select " +
                "(select max(me) from SingleChat si  join si.chat.messages me where si.id=single.id)," +
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
                "single.id, " +
                "single.chat.image," +
                "single.chat.title " +
                "from SingleChat single " +
                "WHERE single.userOne.userId=:id and single.deletedForUserOne=:check OR single.userTwo.userId=:id and single.deletedForUserTwo=:check2 " +
                "ORDER BY single.id ASC")
                .setParameter("id", userId)
                .setParameter("check", false)
                .setParameter("check2", false)
                .unwrap(Query.class)
                .setResultTransformer(new ChatDtoResultTransformer())
                .getResultList();
        chatDtoList.addAll((List<ChatDto>) em.createQuery("select " +
                "(select max(me) from GroupChat si  join si.chat.messages me where si.id=groupchats.id), " +
                "groupchats.chat.image," +
                "groupchats.chat.title," +
                "groupchats.id " +
                "from User user join user.groupChats groupchats " +
                "WHERE user.userId=:id " +
                "ORDER BY groupchats.id ASC")
                .setParameter("id", userId)
                .unwrap(Query.class)
                .setResultTransformer(new GroupChatDtoResultTransformer())
                .getResultList());
        return chatDtoList;
    }

    @Override
    public ChatDto getChatDtoByGroupChatId(Long chatId) {
        return (ChatDto) em.createQuery("select " +
                "(select max(me) from gc.chat.messages me), " +
                "gc.chat.image," +
                "gc.chat.title," +
                "gc.id " +
                "from GroupChat gc where gc.id=:id")
                .setParameter("id", chatId)
                .unwrap(Query.class)
                .setResultTransformer(new GroupChatDtoResultTransformer())
                .getSingleResult();
    }

    @Override
    public List<ChatDto> getAllFavoriteChatDto(Long userId) {
        ChatDtoDaoImpl.userId = userId;
        List<ChatDto> chatDtoList = (List<ChatDto>) em.createQuery("select " +
                "(select max(me) from SingleChat si  join si.chat.messages me where si.id=single.id)," +
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
                "single.id, " +
                "single.chat.image," +
                "single.chat.title " +
                "from SingleChat single right join FavoriteChat fc on single.chat.id = fc.chat.id where fc.user.id = :id " +
                "ORDER BY single.id ASC")
                .setParameter("id", userId)
                .unwrap(Query.class)
                .setResultTransformer(new ChatDtoResultTransformer())
                .getResultList();
        chatDtoList.addAll((List<ChatDto>) em.createQuery("select " +
                "(select max(me) from GroupChat si  join si.chat.messages me where si.id=gc.id), " +
                "gc.chat.image," +
                "gc.chat.title," +
                "gc.id " +
                "from GroupChat gc right join FavoriteChat fc on gc.chat.id = fc.chat.id " +
                "WHERE fc.user.id =:id " +
                "ORDER BY gc.id ASC")
                .setParameter("id", userId)
                .unwrap(Query.class)
                .setResultTransformer(new GroupChatDtoResultTransformer())
                .getResultList());
        return chatDtoList;
    }

    private static class ChatDtoResultTransformer implements ResultTransformer {
        @Override
        public Object transformTuple(Object[] objects, String[] strings) {
            ChatDto chatDto;
            if (((Number) objects[1]).longValue() != userId) {
                chatDto = new ChatDto().builder()
                        .id(((Number) objects[11]).longValue())
                        .title((String)objects[13])
                        .image((String)objects[12])
                        .active((String) objects[6])
                        .lastMessage(((Message) objects[0]).getMessage())
                        .type("singleChats")
                        .build();
            } else {
                chatDto = new ChatDto().builder()
                        .id(((Number) objects[11]).longValue())
                        .title((String)objects[13])
                        .image((String)objects[12])
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
