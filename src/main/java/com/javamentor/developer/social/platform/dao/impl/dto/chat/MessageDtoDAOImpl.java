package com.javamentor.developer.social.platform.dao.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.MessageDtoDAO;
import com.javamentor.developer.social.platform.models.dto.MediaDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageChatDto;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import org.hibernate.Query;
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
    EntityManager em;


    @Override
    @Transactional
    public List<MessageChatDto> getAllMessageDtoByChatId(Long chatId) {
        return (List<MessageChatDto>) em.createQuery("select  message from Chat chat join chat.messages message  where chat.id=:id")
                .setParameter("id", chatId)
                .unwrap(Query.class)
                .setResultTransformer(new MessageDtoResultTransformer())
                .getResultList();
    }

    private static class MessageDtoResultTransformer implements ResultTransformer {
        @Override
        public Object transformTuple(Object[] objects, String[] strings) {
            Message message = null;
            MessageChatDto messageDto = null;
            List<MediaDto> mediaDtoList = new ArrayList<>();
            for (Object o : objects) {
                message = (Message) o;
                messageDto = MessageChatDto.builder()
                        .userSenderImage(message.getUserSender().getAvatar())
                        .persistDate(message.getPersistDate())
                        .lastRedactionDate(message.getLastRedactionDate())
                        .build();
                for (Media media : message.getMedia()) {
                    mediaDtoList.add(MediaDto.builder()
                            .mediaType(media.getMediaType().name())
                            .build());
                }
                messageDto.setMediaDto(mediaDtoList);
            }
            return messageDto;
        }

        @Override
        public List transformList(List list) {
            return list;
        }
    }

}
