package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.dao.impl.dto.chat.ChatDtoDaoImpl;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.chat.ChatEditTitleDto;
import com.javamentor.developer.social.platform.models.entity.chat.Chat;
import com.javamentor.developer.social.platform.models.entity.chat.FavoriteChat;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "datasets/restv2/chat/messages/Messages.yml" ,
        "datasets/restv2/chat/usersChatTest/Active.yml" ,
        "datasets/restv2/chat/usersChatTest/Role.yml" ,
        "datasets/restv2/chat/usersChatTest/User.yml" ,
        "datasets/restv2/chat/chatResources/GroupChat.yml" ,
        "datasets/restv2/chat/chatResources/Chats.yml",
        "datasets/restv2/chat/chatResources/ChatsMessages.yml",
        "datasets/restv2/chat/chatResources/SingleChat.yml" ,
        "datasets/restv2/chat/chatResources/FavoriteChat.yml" ,
        "datasets/restv2/chat/chatResources/UsersGroupChats.yml"}, strategy = SeedStrategy.REFRESH, cleanAfter = true)
@Sql(value = "/create_user_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
public class ChatControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/chats";

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    protected EntityManager entityManager;

    Gson gson = new Gson();

    @Test
    public void getChatsDto() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/chats" , 205))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void getAllFavoriteChatDto() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/chats/favorite"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getChatsByChatName() throws Exception {

        mockMvc.perform(get(apiUrl + "/user/chats")
                .param("currentPage", "1")
                .param("itemsOnPage", "10")
                .param("search", "Admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].title").value("Single chat #5"));

        mockMvc.perform(get(apiUrl + "/user/chats")
                .param("currentPage", "1")
                .param("itemsOnPage", "10")
                .param("search", "wwwww"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid pagination parameters. Parameter 'currentPage' value [1] is greater than total number of available pages [0] considering parameter 'itemsOnPage' value [10]"));


        mockMvc.perform(get(apiUrl + "/user/chats")
                .param("currentPage", "1")
                .param("itemsOnPage", "10")
                .param("search", "adm lastName"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].title").value("Single chat #5"));

        mockMvc.perform(get(apiUrl + "/user/chats")
                .param("currentPage", "1")
                .param("itemsOnPage", "10")
                .param("search", "group"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].title").value("Group chat #1"));

        mockMvc.perform(get(apiUrl + "/user/chats")
                .param("currentPage", "1")
                .param("itemsOnPage", "3")
                .param("search", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3))
                .andExpect(jsonPath("$.items[0].title").value("Single chat #5"))
                .andExpect(jsonPath("$.items[1].title").value("Group chat #1"));

        mockMvc.perform(get(apiUrl + "/user/chats")
                .param("currentPage", "2")
                .param("itemsOnPage", "2")
                .param("search", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].title").value("Group chat #2"));
    }

    @Test
    public void getAllMessageDtoByGroupChatId() throws Exception {
        mockMvc.perform(get(apiUrl + "/group-chats/{chatId}/messages" , 206)
                .param("currentPage" , "1")
                .param("itemsOnPage" , "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].message").value("Test init message13"));

        mockMvc.perform(get(apiUrl + "/group-chats/{chatId}/messages" , 1000)
                .param("currentPage" , "1")
                .param("itemsOnPage" , "10"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Chat id 1000 not found"));
    }

    @Test
    public void getAllMessageDtoBySingleChatId() throws Exception {
        mockMvc.perform(get(apiUrl + "/single-chats/{chatId}/messages" , 202)
                .param("currentPage" , "1")
                .param("itemsOnPage" , "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].message").value("Test init message5"))
                .andExpect(jsonPath("$.items[1].userSenderImage").value("www.myavatar0.ru/9090"));

        mockMvc.perform(get(apiUrl + "/single-chats/{chatId}/messages" , 1000)
                .param("currentPage" , "1")
                .param("itemsOnPage" , "10"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Chat id 1000 not found"));
    }

    @Test
    public void editGroupChatTitle() throws Exception {
        ChatEditTitleDto chatEditTitleDto = ChatEditTitleDto.builder()
                .title("NewTitle")
                .id(206L)
                .build();

        mockMvc.perform(put(apiUrl + "/group-chats/title")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(chatEditTitleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("NewTitle"));
    }

    @Test
    public void deleteUserFromSingleChat() throws Exception {
        mockMvc.perform(delete(apiUrl + "/single-chats/{chatId}/user/{userId}" , 200 , 200))
                .andExpect(status().isOk())
                .andExpect(content().string("done delete chat from user"));
        SingleChat singleChat = (SingleChat) entityManager.createQuery("SELECT s from SingleChat as s where s.id = 200")
               .getSingleResult();
        assertEquals(singleChat.isDeletedForUserOne(), true);
        assertEquals(singleChat.isDeletedForUserTwo(), false);

        mockMvc.perform(delete(apiUrl + "/single-chats/{chatId}/user/{userId}" , 201 , 1000))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("user not found"));

        mockMvc.perform(delete(apiUrl + "/single-chats/{chatId}/user/{userId}" , 1000 , 201))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Single chat not found"));

        mockMvc.perform(delete(apiUrl + "/single-chats/{chatId}/user/{userId}" , 202 , 201))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No such user in chat"));
    }

    @Test
    public void createGroupChat() throws Exception {
        ChatDto chatDto = ChatDto.builder()
                .image("MyImage123")
                .title("MyTitle123")
                .build();

        /*
        кусок ниже не пашет по причине не возможности передать principal
        */
//        mockMvc.perform(post(apiUrl + "/group-chats")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(chatDto)))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("User not found"));

        mockMvc.perform(post(apiUrl + "/group-chats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(chatDto)))
                .andExpect(status().isOk());

    GroupChat groupChat = (GroupChat) entityManager.createQuery(
            "SELECT g " +
                    "FROM GroupChat as g " +
                    "JOIN FETCH g.chat " +
                    "WHERE g.chat.title = :title")
            .setParameter("title", "MyTitle123").getSingleResult();
    assertEquals(groupChat.getChat().getImage(), "MyImage123");
    }

    /**
     * Пришлось изменить датасеты SingleChat и SingleChatMessages потому что триклятый сиквенс
     * Пытается создать чат с айди 1 и падает на констрейнтах.
     * Я не понимаю, у меня одного проблемы с сиквенсом который один на все таблицы и только спит и видит,
     * как уронить все случайно родив айди, который уже есть в данной таблице.
     * Может я тупой или меня это вообще не касается, но какой нам плюс от того,
     * Что у users id начинается с 1, а у albums с 2129, это хоть как-то облегчает навигацию?
     */

    @Test
/*    @DataSet(cleanBefore = true, cleanAfter = true)*/
    public void createSingleChat() throws Exception {
        ChatDto chatDto = ChatDto.builder()
                .image("VeryUniqueImage")
                .title("VeryUniqueTitle")
                .build();

        int userId = 200;

        MockHttpServletResponse response =
                mockMvc.perform(post(String.format("%s%s%d",apiUrl,"/single-chats/user/",userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(chatDto)))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        assertNotNull(response.getContentAsString());
        ChatDto responseContent = gson.fromJson(response.getContentAsString() , ChatDto.class);
        assertEquals(chatDto.getTitle(), responseContent.getTitle());

        TypedQuery<SingleChat> query = entityManager.createQuery("select c from SingleChat c where c.chat.title=:title" , SingleChat.class)
                .setParameter("title" , chatDto.getTitle());
        Optional<TypedQuery<SingleChat>> checkDB = Optional.of(query);

        assertTrue(checkDB.isPresent());
    }


    @Test
    public void createSingleChatWrongUserId() throws Exception {
        ChatDto chatDto = ChatDto.builder()
                .image("MyImage")
                .title("MyTitle")
                .build();

        int userId = 1000;

        MockHttpServletResponse response =
                mockMvc.perform(post(String.format("%s%s%d",apiUrl,"/single-chats/user/",userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(chatDto)))
                        .andExpect(status().isNotFound())
                        .andReturn()
                        .getResponse();

        String correctResponse = "User with id: 1000 not found";
        assertEquals(correctResponse, response.getContentAsString());

    }

    @Test
    public void addChatToFavorites() throws Exception {
        ChatDto chatDto = ChatDto.builder()
                .image("MyImage")
                .title("MyTitle")
                .build();
                mockMvc.perform(post(apiUrl + "/user/chats/favorite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(chatDto)))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();
    }

}