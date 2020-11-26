package com.javamentor.developer.social.platform.service.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.MessageDtoDao;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.MessagePaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class MessageDtoServiceImplImpl extends MessagePaginationServiceImpl implements MessageDtoService {

    private final MessageDtoDao dao;

    @Autowired
    public MessageDtoServiceImplImpl(MessageDtoDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public PageDto<MessageDto, ?> getAllMessageDtoFromGroupChatByChatId(Map<String, Object> parameters) {
        return super.getMessagePageDto("getAllMessagesFromGroupChat", parameters);
    }

    @Override
    @Transactional
    public PageDto<MessageDto, ?> getAllMessageDtoFromSingleChatByChatId(Map<String, Object> parameters) {
        return super.getMessagePageDto("getAllMessagesFromSingleChat", parameters);
    }
}
