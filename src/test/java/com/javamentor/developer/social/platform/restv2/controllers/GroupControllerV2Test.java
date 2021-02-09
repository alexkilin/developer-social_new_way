package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupCategoryDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupCategoryDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupHasUserInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupUpdateInfoDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupCategoryDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupHasUserService;
import liquibase.database.core.MockDatabase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "datasets/restv2/groupset/group/usersResources/User.yml" ,
        "datasets/restv2/groupset/group/usersResources/Active.yml" ,
        "datasets/restv2/groupset/group/usersResources/Role.yml" ,
        "datasets/restv2/groupset/group/Group.yml" ,
        "datasets/restv2/groupset/group/GroupHasUser.yml" ,
        "datasets/restv2/groupset/group/GroupWal.yml" ,
        "datasets/restv2/groupset/group/GroupCategory.yml" ,
        "datasets/restv2/groupset/group/postResources/media.yml" ,
        "datasets/restv2/groupset/group/postResources/post_media.yml" ,
        "datasets/restv2/groupset/group/postResources/post_tags.yml" ,
        "datasets/restv2/groupset/group/postResources/posts.yml" ,
        "datasets/restv2/groupset/group/postResources/tags.yml" ,
        "datasets/restv2/groupset/group/postResources/topics.yml"}
        , strategy = SeedStrategy.REFRESH, cleanAfter = true)
@Sql(value = "/create_user_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
public class GroupControllerV2Test extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupCategoryDtoService groupCategoryDtoService;

    @Autowired
    private GroupDtoService groupDtoService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    GroupHasUserService groupHasUserService;

    private final String apiUrl = "/api/v2/groups";

    private final Gson gson = new Gson();

    @Test
    public void getAllGroups() throws Exception {
        mockMvc.perform(get("/api/v2/groups?currentPage=1&itemsOnPage=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3));
    }

    @Test
    public void showGroup() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}" , 200))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(200))
                .andExpect(jsonPath("name").value("JAVA IS 0"))
                .andExpect(jsonPath("lastRedactionDate").value("2020-08-13T11:46:35.493"))
                .andExpect(jsonPath("persistDate").value("2020-08-13T11:46:35.493"))
                .andExpect(jsonPath("linkSite").value("www.groupsite0.ru"))
                .andExpect(jsonPath("groupCategory").value("Programming"))
                .andExpect(jsonPath("ownerFio").value("LastNameUser0 Admin65"))
                .andExpect(jsonPath("description").value("This is a description of the group #0"));
    }

    @Test
    void showGroupInvalidId() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}" , 1000))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Group id 1000 not found"));
    }

    @Test
    void showGroupWall() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}/posts" , 200)
                .param("currentPage" , "1")
                .param("itemsOnPage" , "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2));
    }

    @Test
    void showGroupWallInvalidId() throws Exception {
        this.mockMvc.perform(get("/api/v2/groups/{groupId}/posts?currentPage=1&itemsOnPage=2" , 1000))
                .andExpect(status().isBadRequest());

    }

    @Test
    void findGroupByName() throws Exception {
        mockMvc.perform(get("/api/v2/groups/name?name=JAVA IS 0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(200))
                .andExpect(jsonPath("name").value("JAVA IS 0"))
                .andExpect(jsonPath("groupCategory").value("Programming"))
                .andExpect(jsonPath("subscribers").value(1));
    }

    @Test
    void findGroupByInvalidName() throws Exception {
        mockMvc.perform(get("/api/v2/groups/name?name=JavaGroupTest"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Group name JavaGroupTest not found"));
    }

    @Test
    void getUsersFromTheGroup() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}/users?currentPage=1&itemsOnPage=2" , 200))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));
    }

    @Test
    void getUsersFromTheGroupInvalidId() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}/users?currentPage=1&itemsOnPage=2" , 1000))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Group id 1000 not found"));
    }

    @Test
    void userJoinGroup() throws Exception {
        mockMvc.perform(put("/api/v2/groups/{groupId}/users?userId=205" , 200))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id: 205 added to the group with id: 200"));
    }

    @Test
    void userJoinGroupExist() throws Exception {
        User user = (User) entityManager.createQuery("SELECT u FROM User u where u.userId = 205")
                .getSingleResult();
        assertThat(groupHasUserService.verificationUserInGroup(200l, user.getUserId())).isFalse();

        mockMvc.perform(put("/api/v2/groups/{groupId}/users?userId=205" , 200))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id: 205 added to the group with id: 200"));


        User userExists = (User) entityManager.createQuery("SELECT u FROM User u where u.userId = 205")
                .getSingleResult();
        assertThat(groupHasUserService.verificationUserInGroup(200l, userExists.getUserId())).isTrue();

    }


    @Test
    void userJoinGroupInvalidId() throws Exception {
        mockMvc.perform(put("/api/v2/groups/{groupId}/users?userId=1000" , 200))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUserById() throws Exception {
        mockMvc.perform(delete("/api/v2/groups/{groupId}/users?userId=200" , 200))
                .andExpect(status().isOk())
                .andExpect(content().string("User with id: 200 is no longer a member of the group with id: 200"));
    }

    @Test
    void deleteUserByInvalidId() throws Exception {
        mockMvc.perform(delete("/api/v2/groups/{groupId}/users?userId=1" , 100))
                .andExpect(status().isBadRequest());
    }

    @Test
    void groupHasUser() throws Exception {

        int groupId = 200;
        String userId = "200";
        MockHttpServletResponse response =
                mockMvc.perform(get(apiUrl + "/" + groupId + "/users")
                        .param("userId" , userId))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        assertNotNull(response.getContentAsString());
        GroupHasUserInfoDto responseContent = gson.fromJson(response.getContentAsString() , GroupHasUserInfoDto.class);
        assertTrue(responseContent.isGroupHasUser());


    }

    @Test
    void groupHasUserWrongId() throws Exception {

        int groupId = 200;
        String userId = "1000";
        MockHttpServletResponse response =
                mockMvc.perform(get(apiUrl + "/" + groupId + "/users")
                        .param("userId" , userId))
                        .andExpect(status().isBadRequest())
                        .andReturn()
                        .getResponse();

        String correctResponse = "User with id: 1000 or/and group with id: 200 not found";
        assertEquals(correctResponse , response.getContentAsString());

    }

    @Test
    void updateGroup() throws Exception {
        GroupUpdateInfoDto groupUpdateInfoDto =
                GroupUpdateInfoDto.builder()
                        .id(200L)
                        .groupCategory("Programming")
                        .linkSite("TEST.SITE/NAME")
                        .name("TEST GROUP NAME")
                        .build();

        MockHttpServletResponse response = mockMvc.perform(put(apiUrl + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(groupUpdateInfoDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();


        GroupDto checkDB = groupDtoService.getGroupById(200L).get();
        assertEquals(checkDB.getName(), groupUpdateInfoDto.getName());

        assertNotNull(response.getContentAsString());
        GroupUpdateInfoDto responseContent = gson.fromJson(response.getContentAsString() , GroupUpdateInfoDto.class);

        assertEquals(groupUpdateInfoDto , responseContent);


    }

    @Test
    void updateGroupWrongGroupId() throws Exception {
        GroupUpdateInfoDto groupUpdateInfoDto =
                GroupUpdateInfoDto.builder()
                        .id(1000L)
                        .groupCategory("Programming")
                        .linkSite("TEST.SITE/NAME")
                        .name("TEST GROUP NAME")
                        .build();

        MockHttpServletResponse response = mockMvc.perform(put(apiUrl + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(groupUpdateInfoDto)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        String correctResponse = "Group with id 1000 not found";
        assertEquals(correctResponse , response.getContentAsString());


    }

    @Test
    void addGroupCategory() throws Exception {
        GroupCategoryDto groupCategoryDto = GroupCategoryDto.builder()
                .name("TEST CATEGORY")
                .build();

        MockHttpServletResponse response = mockMvc.perform(post(apiUrl + "/groupCategory/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(groupCategoryDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertNotNull(response.getContentAsString());
        GroupCategoryDto responseContent = gson.fromJson(response.getContentAsString() , GroupCategoryDto.class);


        Optional<GroupCategoryDto> checkDB = groupCategoryDtoService.getGroupCategoryByName("TEST CATEGORY");
        assertTrue(checkDB.isPresent());

        assertEquals(checkDB.get(), responseContent);


    }

    @Test
    void addGroupCategoryExistingCategory() throws Exception {
        GroupCategoryDto groupCategoryDto = GroupCategoryDto.builder()
                .name("Programming")
                .build();

        MockHttpServletResponse response = mockMvc.perform(post(apiUrl + "/groupCategory/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(groupCategoryDto)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        String correctResponse = "Category with name \"Programming\" already exist";
        assertEquals(correctResponse , response.getContentAsString());

    }


    @Test
    void getAllCategoriesPagination() throws Exception {

        MultiValueMap<String, String> pageArguments = new LinkedMultiValueMap<String, String>() {{
            add("currentPage" , "1");
            add("itemsOnPage" , "15");
        }};

        MockHttpServletResponse response = mockMvc.perform(get(apiUrl + "/groupCategory/all/pageable")
                .params(pageArguments))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertNotNull(response.getContentAsString());
        PageDto responseContent = gson.fromJson(response.getContentAsString() , PageDto.class);

        Map<String, Object> parameters = new LinkedHashMap<String, Object>() {{
            put("currentPage" , 1);
            put("itemsOnPage" , 15);
        }};

        PageDto checkDB = groupCategoryDtoService.getAllCategories(parameters);

        assertEquals(checkDB.getTotalResults() , responseContent.getTotalResults());

    }

    @Test
    void getGroupsByCategory() throws Exception {

        MultiValueMap<String, String> pageArguments = new LinkedMultiValueMap<String, String>() {{
            add("category" , "Programming");
            add("currentPage" , "1");
            add("itemsOnPage" , "15");
        }};

        MockHttpServletResponse response = mockMvc.perform(get(apiUrl + "/sort/byCategory")
                .params(pageArguments))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertNotNull(response.getContentAsString());
        PageDto responseContent = gson.fromJson(response.getContentAsString() , PageDto.class);

        Map<String, Object> parameters = new LinkedHashMap<String, Object>() {{
            put("category" , "Programming");
            put("currentPage" , 1);
            put("itemsOnPage" , 15);
        }};

        PageDto checkDB = groupDtoService.getGroupsByCategory(parameters);

        assertEquals(checkDB.getTotalResults() , responseContent.getTotalResults());

    }

    @Test
    void getGroupsByCategoryNoGroupsFound() throws Exception {

        GroupCategoryDto groupCategoryDto = GroupCategoryDto.builder()
                .name("TEST")
                .build();

        mockMvc.perform(post(apiUrl + "/groupCategory/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(groupCategoryDto)));

        Optional<GroupCategoryDto> test = groupCategoryDtoService.getGroupCategoryByName("TEST");
        assertTrue(test.isPresent());

        MultiValueMap<String, String> pageArguments = new LinkedMultiValueMap<String, String>() {{
            add("category" , "TEST");
            add("currentPage" , "1");
            add("itemsOnPage" , "15");
        }};

        MockHttpServletResponse response = mockMvc.perform(get(apiUrl + "/sort/byCategory")
                .params(pageArguments))
                .andExpect(status().is(412))
                .andReturn()
                .getResponse();

        String correctResponse = "There are no groups containing category named \"TEST\"";
        assertEquals(correctResponse , response.getContentAsString());

    }

    @Test
    void getGroupsByCategoryNotExist() throws Exception {

        MultiValueMap<String, String> pageArguments = new LinkedMultiValueMap<String, String>() {{
            add("category" , "TEST");
            add("currentPage" , "1");
            add("itemsOnPage" , "15");
        }};

        MockHttpServletResponse response = mockMvc.perform(get(apiUrl + "/sort/byCategory")
                .params(pageArguments))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        String correctResponse = "No category named \"TEST\"";
        assertEquals(correctResponse , response.getContentAsString());

    }


}
