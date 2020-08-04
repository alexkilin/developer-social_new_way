package com.javamentor.developer.social.platform.dao.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.ChatDtoDAO;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ChatDtoDAOImpl implements ChatDtoDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<ChatDto> getAllChatDtoByUserId(Long userId) {
        return (List<ChatDto>) em.createQuery("select chat from User user  join  user.chats chat  where user.userId =:id ")
                .setParameter("id",userId)
                .unwrap(Query.class)
                .setResultTransformer(new ChatDtoResultTransformer())
                .getResultList();
    }
    private static class ChatDtoResultTransformer implements ResultTransformer {
        @Override
        public Object transformTuple(Object[] objects, String[] strings) {
            List<UserDto> userDtoList = new ArrayList<>();
            ChatDto iterChat = null;
            for (Object i: objects){
                Chat chat = (Chat) i;
                iterChat = ChatDto.builder()
                        .title(chat.getTitle())
                        .persistDate(chat.getPersistDate())
                        .build();
                for (User v: chat.getUsers()){
                    UserDto iterUser = UserDto.builder()
                            .userId(v.getUserId())
                            .firstName(v.getFirstName())
                            .avatar(v.getAvatar())
                            .aboutMe(v.getAboutMe())
                            .email(v.getEmail())
                            .city(v.getCity())
                            .lastName(v.getLastName())
                            .persistDate(v.getPersistDate())
                            .dateOfBirth(v.getDateOfBirth())
                            .lastRedactionDate(v.getLastRedactionDate())
                            .linkSite(v.getLinkSite())
                            .education(v.getEducation())
                            .id_enable(v.getId_enable())
                            .build();
                    userDtoList.add(iterUser);
                }
                iterChat.setUsers(userDtoList);
            }
            return iterChat;
        }

        @Override
        public List transformList(List list) {
            return list;
        }

    }
}
