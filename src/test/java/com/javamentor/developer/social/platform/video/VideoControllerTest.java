package com.javamentor.developer.social.platform.video;

import com.github.database.rider.core.api.dataset.DataSet;

import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "datasets/video/usersVideoTest/Active.yml",
        "datasets/video/usersVideoTest/User.yml",
        "datasets/video/usersVideoTest/Role.yml",
        "datasets/video/usersVideoTest/Status.yml",
        "datasets/video/usersVideoTest/UsersVideosCollections.yml",
        "datasets/video/Media.yml",
        "datasets/video/albumVideoTest/VideoAlbum.yml",
        "datasets/video/albumVideoTest/Album.yml",
        "datasets/video/albumVideoTest/UserHasAlbum.yml",
        "datasets/video/albumVideoTest/AlbumHasVideo.yml",
        "datasets/video/Video.yml"}, cleanBefore = true, cleanAfter = true)
public class VideoControllerTest extends AbstractIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllVideos() throws Exception {
        this.mockMvc.perform(get("/api/videos/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6));
    }

    @Test
    public void getPartVideo() throws Exception {
        this.mockMvc.perform(get("/api/videos/getPart?currentPage=1&itemsOnPage=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].icon").value("TestIcon2"))
                .andExpect(jsonPath("$[0].name").value("VideoTestName 2"))
                .andExpect(jsonPath("$[1].id").value(4))
                .andExpect(jsonPath("$[1].icon").value("TestIcon3"))
                .andExpect(jsonPath("$[1].name").value("VideoTestName 3"));
    }

    @Test
    public void getVideoOfName() throws Exception {
        this.mockMvc.perform(get("/api/videos/name/{name}", "VideoTestName 2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.icon").value("TestIcon2"))
                .andExpect(jsonPath("$.name").value("VideoTestName 2"));
    }

    @Test
    public void getAudioOfInvalidName() throws Exception {
        this.mockMvc.perform(get("/api/videos/name/{name}", "VideoTestName 55555"))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(content().string("Invalid parameters"));
    }

    @Test
    public void getVideoOfUser() throws Exception {
        this.mockMvc.perform(get("/api/videos/user/{userId}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].icon").value("TestIcon0"))
                .andExpect(jsonPath("$[0].name").value("VideoTestName 0"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].icon").value("TestIcon1"))
                .andExpect(jsonPath("$[1].name").value("VideoTestName 1"));
    }

    @Test
    public void getPartVideoOfUser() throws Exception {
        this.mockMvc.perform(get("/api/videos/PartVideoOfUser/{userId}?currentPage=0&itemsOnPage=2",
                "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].icon").value("TestIcon0"))
                .andExpect(jsonPath("$[0].name").value("VideoTestName 0"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].icon").value("TestIcon1"))
                .andExpect(jsonPath("$[1].name").value("VideoTestName 1"));
    }


    @Test
    public void addInAlbums() throws Exception {
        this.mockMvc.perform(post("/api/videos/addInAlbums?albumId=1&videoId=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = {
            "datasets/video/usersVideoTest/Active.yml",
            "datasets/video/usersVideoTest/User.yml",
            "datasets/video/usersVideoTest/Role.yml",
            "datasets/video/usersVideoTest/Status.yml"}, cleanBefore = true, cleanAfter = true)
    public void createAlbum() throws Exception {

        this.mockMvc.perform(post("/api/videos/createAlbum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"albumVideo\"," +
                        "\"icon\": \"iconTest\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("name").value("albumVideo"))
                .andExpect(jsonPath("icon").value("iconTest"));
    }

    @Test
    @DataSet(value = {
        "datasets/video/usersVideoTest/Active.yml",
        "datasets/video/usersVideoTest/User.yml",
        "datasets/video/usersVideoTest/Role.yml",
        "datasets/video/usersVideoTest/Status.yml"}, cleanBefore = true, cleanAfter = true)
    public void createAlbumsWithSameName() throws Exception {

        this.mockMvc.perform(post("/api/videos/createAlbum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"albumVideo\"," +
                        "\"icon\": \"iconTest\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("name").value("albumVideo"))
                .andExpect(jsonPath("icon").value("iconTest"));

        this.mockMvc.perform(post("/api/videos/createAlbum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"albumVideo\"," +
                        "\"icon\": \"iconTest\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Video album with name 'albumVideo' already exists"));
    }

    @Test
    @DataSet(value = {
            "datasets/video/usersVideoTest/Active.yml",
            "datasets/video/usersVideoTest/User.yml",
            "datasets/video/usersVideoTest/Role.yml",
            "datasets/video/usersVideoTest/Status.yml"}, cleanBefore = true, cleanAfter = true)
    public void createAlbumWithIncorrectName() throws Exception {

        this.mockMvc.perform(post("/api/videos/createAlbum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"\"," +
                        "\"icon\": \"iconTest\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .string("'name' Must not be empty when creating VideoAlbum.class"));

        this.mockMvc.perform(post("/api/videos/createAlbum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"icon\": \"iconTest\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .string("'name' Must not be null when creating VideoAlbum.class"));
    }

    @Test
    public void getFromAlbumOfUser() throws Exception {
        this.mockMvc.perform(get("/api/videos/getFromAlbumOfUser?albumId=1"))
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllAlbums() throws Exception {
        this.mockMvc.perform(get("/api/videos/getAllAlbumsFromUser"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }
      @Test
    public void getAlbumVideoOfUser() throws Exception {
        this.mockMvc.perform(get("/api/videos/AlbumVideoOfUser?album=videoAlbum1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].icon").value("TestIcon0"))
                .andExpect(jsonPath("$[0].name").value("VideoTestName 0"));
   }
}
