package com.javamentor.developer.social.platform.service.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.MessageDtoDao;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageDtoServiceImpl implements MessageDtoService {

    @Autowired
    private MessageDtoDao dao;

    @Override
    @Transactional
    public List<MessageDto> getAllMessageDtoFromGroupChatByChatId(Long chatId) {
        return dao.getAllMessageDtoFromGroupChatByChatId(chatId);
    }

    @Override
    @Transactional
    public List<MessageDto> getAllMessageDtoFromSingleChatByChatId(Long chatId) {
        return dao.getAllMessageDtoFromSingleChatByChatId(chatId);
    }
}
