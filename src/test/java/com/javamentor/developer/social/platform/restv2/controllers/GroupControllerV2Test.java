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
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupCategoryDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(statements = {
        "Insert into active(id, name) values(3, 'test')" ,
        "Insert into role(id, name) values(1, 'USER')" ,

        "Insert into Users(user_id,first_name, last_name, email, last_redaction_date, persist_date, active_id, role_id) " +
                "values (666,'user666','user666', 'admin666@user.ru', '2020-08-04 16:42:03.157535', '2020-08-04 16:42:03.157535', 3, 1)" ,
})
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
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
public class GroupControllerV2Test extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupCategoryDtoService groupCategoryDtoService;

    @Autowired
    private GroupDtoService groupDtoService;

    private final String apiUrl = "/api/v2/groups";

    private final Gson gson = new Gson();

    @Test
    public void getAllGroups() throws Exception {
        mockMvc.perform(get("/api/v2/groups?currentPage=1&itemsOnPage=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3));
    }

    @Test
    public void showGroup() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}" , 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("JAVA IS 0"))
                .andExpect(jsonPath("lastRedactionDate").value("2020-08-13T11:46:35.493"))
                .andExpect(jsonPath("persistDate").value("2020-08-13T11:46:35.493"))
                .andExpect(jsonPath("linkSite").value("www.groupsite0.ru"))
                .andExpect(jsonPath("groupCategory").value("Programming"))
                .andExpect(jsonPath("ownerFio").value("LastNameUser0 Admin0"))
                .andExpect(jsonPath("description").value("This is a description of the group #0"));
    }

    @Test
    void showGroupInvalidId() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}" , 100))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Group id 100 not found"));
    }

    @Test
    void showGroupWall() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}/posts" , 1)
                .param("currentPage" , "1")
                .param("itemsOnPage" , "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2));
    }

    @Test
    void showGroupWallInvalidId() throws Exception {
        this.mockMvc.perform(get("/api/v2/groups/{groupId}/posts?currentPage=1&itemsOnPage=2" , 100))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void findGroupByName() throws Exception {
        mockMvc.perform(get("/api/v2/groups/name?name=JAVA IS 0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("JAVA IS 0"))
                .andExpect(jsonPath("groupCategory").value("Programming"))
                .andExpect(jsonPath("subscribers").value(1));
    }

    @Test
    void findGroupByInvalidName() throws Exception {
        mockMvc.perform(get("/api/v2/groups/name?name=JavaGroupTest"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Group name JavaGroupTest not found"));
    }

    @Test
    void getUsersFromTheGroup() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}/users?currentPage=1&itemsOnPage=2" , 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));
    }

    @Test
    void getUsersFromTheGroupInvalidId() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}/users?currentPage=1&itemsOnPage=2" , 100))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Group id 100 not found"));
    }

    @Test
    void userJoinGroup() throws Exception {
        mockMvc.perform(put("/api/v2/groups/{groupId}/users?userId=20" , 4))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User with id: 20 added to the group with id: 4"));
    }

    @Test
    void userJoinGroupExist() throws Exception {
        mockMvc.perform(put("/api/v2/groups/{groupId}/users?userId=20" , 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User with id: 20 already a member of the group with id: 2"));
    }


    @Test
    void userJoinGroupInvalidId() throws Exception {
        mockMvc.perform(put("/api/v2/groups/{groupId}/users?userId=100" , 100))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUserById() throws Exception {
        mockMvc.perform(delete("/api/v2/groups/{groupId}/users?userId=60" , 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User with id: 60 is no longer a member of the group with id: 1"));
    }

    @Test
    void deleteUserByInvalidId() throws Exception {
        mockMvc.perform(delete("/api/v2/groups/{groupId}/users?userId=1" , 100))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void groupHasUser() throws Exception {

        int groupId = 4;
        String userId = "40";
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

        int groupId = 4;
        String userId = "100500";
        MockHttpServletResponse response =
                mockMvc.perform(get(apiUrl + "/" + groupId + "/users")
                        .param("userId" , userId))
                        .andExpect(status().isBadRequest())
                        .andReturn()
                        .getResponse();

        String correctResponse = "User with id: 100500 or/and group with id: 4 not found";
        assertEquals(correctResponse , response.getContentAsString());

    }

    @Test
    void updateGroup() throws Exception {
        GroupUpdateInfoDto groupUpdateInfoDto =
                GroupUpdateInfoDto.builder()
                        .id(4L)
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


        GroupDto checkDB = groupDtoService.getGroupById(4L).get();
        assertEquals(checkDB.getName(), groupUpdateInfoDto.getName());

        assertNotNull(response.getContentAsString());
        GroupUpdateInfoDto responseContent = gson.fromJson(response.getContentAsString() , GroupUpdateInfoDto.class);

        assertEquals(groupUpdateInfoDto , responseContent);


    }

    @Test
    void updateGroupWrongGroupId() throws Exception {
        GroupUpdateInfoDto groupUpdateInfoDto =
                GroupUpdateInfoDto.builder()
                        .id(100500L)
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

        String correctResponse = "Group with id 100500 not found";
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
