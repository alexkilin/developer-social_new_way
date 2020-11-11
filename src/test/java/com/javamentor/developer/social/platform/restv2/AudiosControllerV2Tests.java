package com.javamentor.developer.social.platform.restv2;


import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "datasets/restv2/audio/usersAudioTest/Active.yml",
        "datasets/restv2/audio/usersAudioTest/User.yml",
        "datasets/restv2/audio/usersAudioTest/Role.yml",
        "datasets/restv2/audio/usersAudioTest/UsersAudiosCollections.yml",
        "datasets/restv2/audio/Media.yml",
        "datasets/restv2/audio/albumAudioTest/AudioAlbum.yml",
        "datasets/restv2/audio/albumAudioTest/Album.yml",
        "datasets/restv2/audio/albumAudioTest/UserHasAlbum.yml",
        "datasets/restv2/audio/albumAudioTest/AlbumHasAudio.yml",
        "datasets/restv2/audio/Audio.yml"}, cleanBefore = true, cleanAfter = true)
class AudiosControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/audio";
    
    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    @Test
    public void getAllAudioPaged() throws Exception {
        this.mockMvc.perform(get(apiUrl + "/")
                .param("currentPage", "0")
                .param("itemsOnPage", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].url").value("www.myaudio1.ru"))
                .andExpect(jsonPath("$[0].icon").value("TestIcon0"))
                .andExpect(jsonPath("$[0].name").value("AudioTestName 0"))
                .andExpect(jsonPath("$[0].author").value("Test Author 0"))
                .andExpect(jsonPath("$[0].album").value("AlbumTestName 0"))
                .andExpect(jsonPath("$[0].length").value(365))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[4].id").value(5));
    }

//    @Test
//    public void getPartAudios() throws Exception {
//        this.mockMvc.perform(get("/api/audios/getPart?currentPage=1&itemsOnPage=2"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(3))
//                .andExpect(jsonPath("$[0].album").value("AlbumTestName 2"))
//                .andExpect(jsonPath("$[0].author").value("Test Author 2"))
//                .andExpect(jsonPath("$[0].icon").value("TestIcon2"))
//                .andExpect(jsonPath("$[0].name").value("AudioTestName 2"))
//                .andExpect(jsonPath("$[1].id").value(4))
//                .andExpect(jsonPath("$[1].album").value("AlbumTestName 3"))
//                .andExpect(jsonPath("$[1].author").value("Test Author 3"))
//                .andExpect(jsonPath("$[1].icon").value("TestIcon3"))
//                .andExpect(jsonPath("$[1].name").value("AudioTestName 3"));
//    }
//
//    @Test
//    public void getAudioOfAuthor() throws Exception {
//        this.mockMvc.perform(get("/api/audios/author/{author}", "Test Author 2"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(3))
//                .andExpect(jsonPath("$[0].album").value("AlbumTestName 2"))
//                .andExpect(jsonPath("$[0].author").value("Test Author 2"))
//                .andExpect(jsonPath("$[0].icon").value("TestIcon2"))
//                .andExpect(jsonPath("$[0].name").value("AudioTestName 2"))
//                .andExpect(jsonPath("$[1].id").value(6))
//                .andExpect(jsonPath("$[1].album").value("AlbumTestName 5"))
//                .andExpect(jsonPath("$[1].author").value("Test Author 2"))
//                .andExpect(jsonPath("$[1].icon").value("TestIcon5"))
//                .andExpect(jsonPath("$[1].name").value("AudioTestName 5"));
//    }
//
//    @Test
//    public void getAudioOfInvalidAuthor() throws Exception {
//        this.mockMvc.perform(get("/api/audios/author/{author}", "Test Author 999"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(0));
//
//    }
//
//    @Test
//    public void getAudioOfName() throws Exception {
//        this.mockMvc.perform(get("/api/audios/name/{name}", "AudioTestName 2"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(3))
//                .andExpect(jsonPath("$.album").value("AlbumTestName 2"))
//                .andExpect(jsonPath("$.author").value("Test Author 2"))
//                .andExpect(jsonPath("$.icon").value("TestIcon2"))
//                .andExpect(jsonPath("$.name").value("AudioTestName 2"));
//    }
//
//    @Test
//    public void getAudioOfInvalidName() throws Exception {
//        this.mockMvc.perform(get("/api/audios/name/{name}", "AudioTestName 55555"))
//                .andDo(print())
//                .andExpect(status().is(400))
//                .andExpect(content().string("Invalid parameters"));
//    }
//
//    @Test
//    public void getAudioOfUser() throws Exception {
//        this.mockMvc.perform(get("/api/audios/user/{userId}", "60"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].album").value("AlbumTestName 0"))
//                .andExpect(jsonPath("$[0].author").value("Test Author 0"))
//                .andExpect(jsonPath("$[0].icon").value("TestIcon0"))
//                .andExpect(jsonPath("$[0].name").value("AudioTestName 0"))
//                .andExpect(jsonPath("$[1].id").value(3))
//                .andExpect(jsonPath("$[1].album").value("AlbumTestName 2"))
//                .andExpect(jsonPath("$[1].author").value("Test Author 2"))
//                .andExpect(jsonPath("$[1].icon").value("TestIcon2"))
//                .andExpect(jsonPath("$[1].name").value("AudioTestName 2"));
//    }
//
//    @Test
//    public void getPartAudioOfUser() throws Exception {
//        this.mockMvc.perform(get("/api/audios/PartAudioOfUser/{userId}?currentPage=0&itemsOnPage=2",
//                "60"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].album").value("AlbumTestName 0"))
//                .andExpect(jsonPath("$[0].author").value("Test Author 0"))
//                .andExpect(jsonPath("$[0].icon").value("TestIcon0"))
//                .andExpect(jsonPath("$[0].name").value("AudioTestName 0"))
//                .andExpect(jsonPath("$[1].id").value(3))
//                .andExpect(jsonPath("$[1].album").value("AlbumTestName 2"))
//                .andExpect(jsonPath("$[1].author").value("Test Author 2"))
//                .andExpect(jsonPath("$[1].icon").value("TestIcon2"))
//                .andExpect(jsonPath("$[1].name").value("AudioTestName 2"));
//    }
//
//    @Test
//    public void getAuthorAudioOfUser() throws Exception {
//        this.mockMvc.perform(get("/api/audios/AuthorAudioOfUser?author=Test Author 2"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].id").value(3))
//                .andExpect(jsonPath("$[0].album").value("AlbumTestName 2"))
//                .andExpect(jsonPath("$[0].author").value("Test Author 2"))
//                .andExpect(jsonPath("$[0].icon").value("TestIcon2"))
//                .andExpect(jsonPath("$[0].name").value("AudioTestName 2"));
//    }
//
//    @Test
//    public void getAlbumAudioOfUser() throws Exception {
//        this.mockMvc.perform(get("/api/audios/AlbumAudioOfUser?album=AlbumTestName 2"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].id").value(3))
//                .andExpect(jsonPath("$[0].album").value("AlbumTestName 2"))
//                .andExpect(jsonPath("$[0].author").value("Test Author 2"))
//                .andExpect(jsonPath("$[0].icon").value("TestIcon2"))
//                .andExpect(jsonPath("$[0].name").value("AudioTestName 2"));
//    }
//
//    @Test
//    public void addAudioInCollectionsOfUser() throws Exception {
//        this.mockMvc.perform(post("/api/audios/addToUser?audioId=5&userId=1"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void addInAlbums() throws Exception {
//        this.mockMvc.perform(post("/api/audios/addInAlbums?albumId=1&audioId=1"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DataSet(value = {
//            "datasets/audio/usersAudioTest/Active.yml",
//            "datasets/audio/usersAudioTest/User.yml",
//            "datasets/audio/usersAudioTest/Role.yml"}, cleanBefore = true, cleanAfter = true)
//    public void createAlbum() throws Exception {
//        AlbumDto albumTest = AlbumDto.builder()
//                .name("albumAudio")
//                .icon("iconTest")
//                .build();
//
//        this.mockMvc.perform(post("/api/audios/createAlbum")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(albumTest)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").isNumber())
//                .andExpect(jsonPath("name").value("albumAudio"))
//                .andExpect(jsonPath("icon").value("iconTest"));
//    }
//
//    @Test
//    @DataSet(value = {
//            "datasets/audio/usersAudioTest/Active.yml",
//            "datasets/audio/usersAudioTest/User.yml",
//            "datasets/audio/usersAudioTest/Role.yml"}, cleanBefore = true, cleanAfter = true)
//    public void createAlbumsWithSameName() throws Exception {
//        AlbumDto albumTest = AlbumDto.builder()
//                .name("albumAudio")
//                .icon("iconTest")
//                .build();
//
//        this.mockMvc.perform(post("/api/audios/createAlbum")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(albumTest)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").isNumber())
//                .andExpect(jsonPath("name").value("albumAudio"))
//                .andExpect(jsonPath("icon").value("iconTest"));
//
//        this.mockMvc.perform(post("/api/audios/createAlbum")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(albumTest)))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Audio album with name '" + albumTest.getName() + "' already exists"));
//    }
//
//    @Test
//    @DataSet(value = {
//            "datasets/audio/usersAudioTest/Active.yml",
//            "datasets/audio/usersAudioTest/User.yml",
//            "datasets/audio/usersAudioTest/Role.yml",}, cleanBefore = true, cleanAfter = true)
//    public void createAlbumWithIncorrectName() throws Exception {
//        AlbumDto albumTest = AlbumDto.builder()
//                .name("")
//                .icon("iconTest")
//                .build();
//
//        this.mockMvc.perform(post("/api/audios/createAlbum")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(gson.toJson(albumTest)))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(content()
//                        .string("'name' Must not be empty when creating and updating"));
//
//        albumTest = AlbumDto.builder()
//                .icon("iconTest")
//                .build();
//
//        this.mockMvc.perform(post("/api/audios/createAlbum")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(albumTest)))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(content()
//                        .string("'name' Must not be empty when creating and updating"));
//    }
//
//    @Test
//    public void getFromAlbumOfUser() throws Exception {
//        this.mockMvc.perform(get("/api/audios/getFromAlbumOfUser?albumId=1"))
//                .andDo(print())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void getAllAlbums() throws Exception {
//        this.mockMvc.perform(get("/api/audios/getAllAlbumsFromUser"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(3));
//    }
//
//    @Test
//    void addAudioToPlaylist() throws Exception {
//
//        this.mockMvc.perform(put("/api/audios/playlists/10/audio?audioId=4"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("test0"))
//                .andExpect(jsonPath("$.image").value("testimage0"))
//                .andExpect(jsonPath("$.content.length()").value("4"));
//
//        this.mockMvc.perform(put("/api/audios/playlists/10/audio?audioId=4"))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void deleteAudioFromPlaylist() throws Exception {
//        this.mockMvc.perform(delete("/api/audios/playlists/10/audio/3"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content.length()").value("2"));
//
//        this.mockMvc.perform(delete("/api/audios/playlists/10/audio/3" +
//                ""))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }

}
