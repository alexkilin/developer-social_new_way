package com.javamentor.developer.social.platform.service.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.MessagePaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class MessageDtoServiceImpl extends MessagePaginationService<MessageDto, Object> implements MessageDtoService {
    public MessageDtoServiceImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<MessageDto, Object> getAllMessageDtoFromGroupChatByChatId(Map<String, Object> parameters) {
        return (PageDto<MessageDto, Object>) super.getMessagePageDto("getAllMessagesFromGroupChat", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<MessageDto, Object> getAllMessageDtoFromSingleChatByChatId(Map<String, Object> parameters) {
        return (PageDto<MessageDto, Object>) super.getMessagePageDto("getAllMessagesFromSingleChat", parameters);
    }
}
