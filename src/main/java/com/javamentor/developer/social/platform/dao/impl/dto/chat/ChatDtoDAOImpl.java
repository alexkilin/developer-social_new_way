package com.javamentor.developer.social.platform.dao.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.ChatDtoDAO;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.entity.chat.Chat;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.List;

@Repository
public class ChatDtoDAOImpl implements ChatDtoDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<ChatDto> getAllChatDtoByUserId(Long userId) {
        return (List<ChatDto>) em.createQuery("select chat from User user  join  user.chats chat  where user.userId =:id ")
                .setParameter("id", userId)
                .unwrap(Query.class)
                .setResultTransformer(new ChatDtoResultTransformer())
                .getResultList();
    }

    private static class ChatDtoResultTransformer implements ResultTransformer {
        @Override
        public Object transformTuple(Object[] objects, String[] strings) {
            Chat chat = null;
            ChatDto chatDto = null;
            for (Object o : objects) {
                chat = (Chat) o;
                chatDto = ChatDto.builder()
                        .title(chat.getTitle())
                        .image(chat.getUserReceiver().getAvatar())
                        .active(chat.getUserReceiver().getActive().getName())
                        .build();
                Message max = chat.getMessages().stream().max(Comparator.comparing(Message::getPersistDate)).get();
                chatDto.setLastMessage(max.getMessage());

            }
            return chatDto;
        }

        @Override
        public List transformList(List list) {
            return list;
        }

    }
}
