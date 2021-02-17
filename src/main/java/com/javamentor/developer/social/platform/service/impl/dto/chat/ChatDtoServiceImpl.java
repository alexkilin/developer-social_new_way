package com.javamentor.developer.social.platform.service.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.ChatDtoDao;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.ChatDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.ChatPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Service
public class ChatDtoServiceImpl extends ChatPaginationService<Object, Object> implements ChatDtoService {

    private final ChatDtoDao dao;

    @Autowired
    public ChatDtoServiceImpl(ChatDtoDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public List<ChatDto> getAllChatDtoByUserId(Long userId) {
        return dao.getAllChatDtoByUserId(userId);
    }

    @Override
    @Transactional
    public ChatDto getChatDtoByGroupChatId(Long chatId) {
        return dao.getChatDtoByGroupChatId(chatId);
    }

    @Override
    public PageDto<ChatDto, Object> getChatDtoByChatName(Map<String, Object> parameters) {
        String search = (String) parameters.get("search");
        parameters.put("search", search.trim().replaceAll("\\s+"," ").replace(" ", "% ")+"%");
        return (PageDto<ChatDto, Object>) super.getChatPageDto("getSingleChatDtoByChatName", "getGroupChatDtoByChatName", parameters);
    }
}
