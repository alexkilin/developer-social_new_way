package com.javamentor.developer.social.platform.dao.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.MessageDtoDAO;
import com.javamentor.developer.social.platform.models.dto.chat.MediaDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageDtoDAOImpl implements MessageDtoDAO {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public List<MessageDto> getAllMessageDtoFromGroupChatByChatId(Long chatId) {
        return em.createQuery("select gr.messages from GroupChat gr  where gr.id=:chatId")
                .setParameter("chatId", chatId)
                .unwrap(Query.class)
                .setResultTransformer(new GroupMessageChatResultTransformer())
                .getResultList();

    }

    @Override
    @Transactional
    public List<MessageDto> getAllMessageDtoFromSingleChatByChatId(Long chatId) {
        return em.createQuery("select sc.messages from SingleChat sc where sc.id=:chatId")
                .setParameter("chatId", chatId)
                .unwrap(Query.class)
                .setResultTransformer(new SingleMessageChatResultTransformer())
                .getResultList();
    }

    private static class GroupMessageChatResultTransformer implements ResultTransformer{
        @Override
        public Object transformTuple(Object[] objects, String[] strings) {
            MessageDto messageDto = null;
            for(Object o: objects){
                Message message = (Message) o;
                messageDto = MessageDto.builder()
                        .lastRedactionDate(message.getLastRedactionDate())
                        .persistDate(message.getPersistDate())
                        .userSenderImage(message.getUserSender().getAvatar())
                        .message(message.getMessage())
                        .mediaDto(new ArrayList<>())
                        .build();
                for (Media media: message.getMedia()){
                    messageDto.getMediaDto().add(new MediaDto().builder()
                            .persistDateTime(media.getPersistDateTime())
                            .url(media.getUrl())
                            .id(media.getId())
                            .mediaType(media.getMediaType().name())
                            .build());
                }

            }
            return messageDto;        }

        @Override
        public List transformList(List list) {
            return list;
        }
    }
    private static class SingleMessageChatResultTransformer implements ResultTransformer{
        @Override
        public Object transformTuple(Object[] objects, String[] strings) {
            MessageDto messageDto = null;
            for(Object o: objects){
                Message message = (Message) o;
                messageDto = MessageDto.builder()
                        .lastRedactionDate(message.getLastRedactionDate())
                        .persistDate(message.getPersistDate())
                        .userSenderImage(message.getUserSender().getAvatar())
                        .message(message.getMessage())
                        .active(message.getUserSender().getActive().getName())
                        .mediaDto(new ArrayList<>())
                        .build();
                for (Media media: message.getMedia()){
                    messageDto.getMediaDto().add(new MediaDto().builder()
                            .persistDateTime(media.getPersistDateTime())
                            .url(media.getUrl())
                            .id(media.getId())
                            .mediaType(media.getMediaType().name())
                            .build());
                }

            }
            return messageDto;
        }

        @Override
        public List transformList(List list) {
            return list;
        }
    }
}
