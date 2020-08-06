package com.javamentor.developer.social.platform.service.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.MessageDtoDAO;
import com.javamentor.developer.social.platform.models.dto.chat.MessageChatDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageDtoServiceImpl implements MessageDtoService {

    @Autowired
    private MessageDtoDAO dao;

    @Override
    @Transactional
    public List<MessageChatDto> getAllMessageDtoByChatId(Long chatId) {
        return dao.getAllMessageDtoByChatId(chatId);
    }
}
