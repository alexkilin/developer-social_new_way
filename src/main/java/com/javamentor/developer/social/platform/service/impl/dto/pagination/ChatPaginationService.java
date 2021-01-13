package com.javamentor.developer.social.platform.service.impl.dto.pagination;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ChatPaginationService <T, V> extends PaginationServiceImpl<Object, Object> {

    private Map<String, PaginationDao> pageBeans;

    @Autowired
    public void setPageBeans(Map<String, PaginationDao> pageBeans) {
        this.pageBeans = pageBeans;
    }

    @SuppressWarnings("unchecked")
    public PageDto<? extends T, ? extends V> getChatPageDto(String methodNameSingleChat, String methodNameGroupChat, Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        if (currentPage > 0 && itemsOnPage > 0) {
            PaginationDao paginationSingleChatDao = pageBeans.get(methodNameSingleChat);
            PaginationDao paginationGroupChatDao = pageBeans.get(methodNameGroupChat);

            List<T> chatDtoList = paginationSingleChatDao.getItems(parameters);
            Long countSingleChat = paginationSingleChatDao.getCount(parameters);
            Long countGroupChat = paginationGroupChatDao.getCount(parameters);

            if (chatDtoList.size() < itemsOnPage) {
                int itemsOnLastPageSingleChat = getCountItemsOnLastPageSingleChat(chatDtoList.size(), itemsOnPage, countSingleChat);
                int singlePage = (int) (countSingleChat / itemsOnPage);

                parameters.put("countSingleChat", chatDtoList.size());
                parameters.put("singlePage", singlePage);
                parameters.put("itemsOnLastPageSingleChat", itemsOnLastPageSingleChat);

                chatDtoList.addAll(paginationGroupChatDao.getItems(parameters));
            }

            PageDto<T, V> pageDto = new PageDto<T, V>(currentPage, itemsOnPage,
                    chatDtoList, countSingleChat+countGroupChat);

            if (currentPage > pageDto.getTotalPages()) {
                throw new PaginationException("Invalid pagination parameters. " +
                        String.format("Parameter 'currentPage' value [%d] is greater than total number of available pages [%d] " +
                                        "considering parameter 'itemsOnPage' value [%d]",
                                currentPage, pageDto.getTotalPages(), itemsOnPage));
            }

            return pageDto;
        }

        throw new PaginationException("Invalid pagination parameters. " +
                "Please, make sure that parameters 'currentPage' and 'itemsOnPage' values are greater than 0");
    }

    private int getCountItemsOnLastPageSingleChat (int singleChatOnPage, int itemsOnPage, Long countSingleChat) {
        int itemsOnLastPageSingleChat = 0;

        if (singleChatOnPage == 0) {
            if (countSingleChat < itemsOnPage) {
                itemsOnLastPageSingleChat = countSingleChat.intValue();
            } else {
                itemsOnLastPageSingleChat = (int) (countSingleChat % itemsOnPage);
            }
        }

        return itemsOnLastPageSingleChat;
    }
}
