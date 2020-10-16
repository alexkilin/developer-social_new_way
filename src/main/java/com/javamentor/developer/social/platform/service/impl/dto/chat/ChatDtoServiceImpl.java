package com.javamentor.developer.social.platform.service.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.ChatDtoDao;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.ChatDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatDtoServiceImpl implements ChatDtoService {

    @Autowired
    private ChatDtoDao dao;

    @Override
    @Transactional
    public List<ChatDto> getAllChatDtoByUserId(Long userId) {
        return dao.getAllChatDtoByUserId(userId);
    }

    @Override
    public ChatDto getChatDtoByGroupChatId(Long chatId) {
        return dao.getChatDtoByGroupChatId(chatId);
    }


}
