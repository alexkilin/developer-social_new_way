package com.javamentor.developer.social.platform.service.impl.dto.pagination;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.MessageDtoDao;
import com.javamentor.developer.social.platform.models.dto.chat.MediaDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessagePaginationServiceImpl<T, V> extends PaginationServiceImpl<T, V> {
    private MessageDtoDao messageDtoDao;

    @Autowired
    public void PostPaginationService(MessageDtoDao messageDtoDao) {
        this.messageDtoDao = messageDtoDao;
    }

    public PageDto<T, V> getMessagePageDto(String methodName, Map<String, Object> parameters) {
        PageDto<MessageDto, ?> pageDto;
        try {
            pageDto = (PageDto<MessageDto, ?>) super.getPageDto(methodName, parameters);
        } catch (Exception e) {
            throw new PaginationException("Invalid parameters or declared implementation");
        }
        addMedias(pageDto);

        return (PageDto<T, V>) pageDto;
    }
    
    public void addMedias(PageDto<MessageDto, ?> pageDto) {
        List<MessageDto> getDtoList = pageDto.getItems();
        List<Long> messageIdList = getDtoList.stream().map(MessageDto::getId).collect(Collectors.toList());
        List<MediaDto> mediasForMessage = messageDtoDao.getAllMediasOfMessage(messageIdList);

        Map<Long, List<MediaDto>> medias = new HashMap<>();
        mediasForMessage.forEach(mediaDto -> {
            if (mediaDto != null) {
                if (!medias.containsKey(mediaDto.getMessageId())) {
                    medias.put(mediaDto.getMessageId(), new ArrayList<>());
                }
                medias.get(mediaDto.getMessageId()).add(mediaDto);
            }
        });

        getDtoList.forEach(messageDto -> {
            messageDto.setMediaDto(medias.get(messageDto.getId()));
            if (messageDto.getMediaDto() == null) {
                messageDto.setMediaDto(new ArrayList<>());
            }
        });
    }
}
