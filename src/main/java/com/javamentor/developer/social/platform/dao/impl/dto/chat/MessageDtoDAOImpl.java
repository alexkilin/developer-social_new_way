package com.javamentor.developer.social.platform.dao.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.MessageDtoDAO;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageDtoDAOImpl implements MessageDtoDAO {

    @PersistenceContext
    EntityManager em;


    @Override
    @Transactional
    public List<MessageDto> getAllMessageDtoByChatId(Long chatId) {
        return (List<MessageDto>) em.createQuery("select  message from Chat chat join chat.messages message  where chat.id=:id")
                .setParameter("id", chatId)
                .unwrap(Query.class)
                .setResultTransformer(new MessageDtoResultTransformer())
                .getResultList();
    }
    private static class MessageDtoResultTransformer implements ResultTransformer {
        @Override
        public Object transformTuple(Object[] objects, String[] strings) {
            MessageDto messageDto = null;
            for (Object j: objects) {
                Message message = (Message) j;
                User user = message.getUserSender();
                UserDto userDto = UserDto.builder()
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .avatar(user.getAvatar())
                        .aboutMe(user.getAboutMe())
                        .email(user.getEmail())
                        .city(user.getCity())
                        .lastName(user.getLastName())
                        .persistDate(user.getPersistDate())
                        .dateOfBirth(user.getDateOfBirth())
                        .lastRedactionDate(user.getLastRedactionDate())
                        .linkSite(user.getLinkSite())
                        .education(user.getEducation())
                        .id_enable(user.getId_enable())
                        .build();
                messageDto = MessageDto.builder()
                        .message(message.getMessage())
                        .userSender(userDto)
                        .build();
            }
            return messageDto;
        }

        @Override
        public List transformList(List list) {
            return list;
        }
    }

}
