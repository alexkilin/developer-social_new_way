package com.javamentor.developer.social.platform.restv2.controllers;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(statements = {
        "Insert into active(id, name) values(3, 'test')",
        "Insert into role(id, name) values(1, 'USER')",

        "Insert into Users(user_id,first_name, last_name, email, last_redaction_date, persist_date, active_id, role_id) " +
                "values (666,'user666','user666', 'admin666@user.ru', '2020-08-04 16:42:03.157535', '2020-08-04 16:42:03.157535', 3, 1)",
})
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
@DataSet(value = {
        "datasets/restv2/audio/usersResources/Active.yml",
        "datasets/restv2/audio/usersResources/User.yml",
        "datasets/restv2/audio/usersResources/Role.yml",
        "datasets/restv2/audio/UsersAudiosCollections.yml",
        "datasets/restv2/audio/playlistAudioTest/Playlist.yml",
        "datasets/restv2/audio/playlistAudioTest/PlaylistHasAudios.yml",
        "datasets/restv2/audio/Media.yml",
        "datasets/restv2/audio/albumAudioTest/AudioAlbum.yml",
        "datasets/restv2/audio/albumAudioTest/Album.yml",
        "datasets/restv2/audio/albumAudioTest/UserHasAlbum.yml",
        "datasets/restv2/audio/albumAudioTest/AlbumHasAudio.yml",
        "datasets/restv2/audio/Audio.yml"}, strategy = SeedStrategy.REFRESH, cleanAfter = true)
class AudiosControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/audio";

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();

    @Test
    public void getPartAudios() throws Exception {
        this.mockMvc.perform(get(apiUrl + "/")
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(4))
                .andExpect(jsonPath("$.items[0].id").value(70))
                .andExpect(jsonPath("$.items[0].url").value("www.myaudio7.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon7"))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 7"))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 7"))
                .andExpect(jsonPath("$.items[0].album").value("AlbumTestName 7"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[1].id").value(20))
                .andExpect(jsonPath("$.items[2].id").value(30))
                .andExpect(jsonPath("$.items[3].id").value(40));
    }

    @Test
    public void getAudioOfAuthor() throws Exception {
        mockMvc.perform(get(apiUrl + "/author/{author}", "Test Author 1")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(20))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 1"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[0].album").value("AlbumTestName 1"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon1"));
    }

    @Test
    public void getAudioOfName() throws Exception {
        mockMvc.perform(get(apiUrl + "/name/{name}", "AudioTestName 2")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(30))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 2"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[0].album").value("AlbumTestName 2"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon2"));
    }

    @Test
    public void getAudioOfAlbum() throws Exception {
        mockMvc.perform(get(apiUrl + "/album/{album}", "AlbumTestName 5")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(60))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 2"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 5"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon5"));
    }

    @Test
    public void getPartAudioOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}", 2)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3))
                .andExpect(jsonPath("$.items[0].id").value(30))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 2"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 2"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon2"))
                .andExpect(jsonPath("$.items[1].id").value(40))
                .andExpect(jsonPath("$.items[1].author").value("Test Author 3"))
                .andExpect(jsonPath("$.items[1].length").value(365))
                .andExpect(jsonPath("$.items[1].name").value("AudioTestName 3"))
                .andExpect(jsonPath("$.items[1].icon").value("TestIcon3"));
    }

    @Test
    public void getAuthorAudioOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/author?author=Test Author 1", 3)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(20))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 1"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 1"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon1"));
    }

    @Test
    public void getAlbumAudioOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/album?album=AlbumTestName 7", 2)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(70))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 7"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 7"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon7"));
    }

    @Test
    public void addAudioInCollectionsOfUser() throws Exception {
        mockMvc.perform(put(apiUrl + "/user/audio")
                .param("audioId", "40"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Audio id 40 added to collection of user id 666"));

        mockMvc.perform(put(apiUrl + "/user/audio")
                .param("audioId", "500"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Audio id 500 not found"));
    }

    @Test
    public void addAudio() throws Exception {
        AudioDto audioDto = AudioDto.builder()
                .album("AlbumTestName 200")
                .name("AudioTestName 200")
                .length(200)
                .author("Test Author 200")
                .icon("TestIcon200")
                .url("www.mymusic.ru")
                .build();


        mockMvc.perform(post(apiUrl + "/user/{userId}/audio", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(audioDto)))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post(apiUrl + "/user/{userId}/audio", 900)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(audioDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User id 900 not found"));
    }

    @Test
    public void getAllAlbums() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/album", 2)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(4))
                .andExpect(jsonPath("$.items[0].id").value(20))
                .andExpect(jsonPath("$.items[0].name").value("audioAlbum"))
                .andExpect(jsonPath("$.items[0].icon").value("icon 2"))
                .andExpect(jsonPath("$.items[1].id").value(30))
                .andExpect(jsonPath("$.items[1].name").value("audioAlbum"))
                .andExpect(jsonPath("$.items[1].icon").value("icon 3"));
    }

    @Test
    public void addInAlbums() throws Exception {
        mockMvc.perform(put(apiUrl + "/albums/{albumId}/audio", 40)
                .param("audioId", "20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Audio id 20 added to album id 40"));

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/audio", 800)
                .param("audioId", "20"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album id 800 not found"));

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/audio", 40)
                .param("audioId", "900"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Audio id 900 not found"));
    }

    @Test
    public void createAlbum() throws Exception {
        AlbumDto albumDto = AlbumDto.builder()
                .icon("TestIcon99")
                .name("MyTestAlbum")
                .build();

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MyTestAlbum"));

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Audio album with name 'MyTestAlbum' already exists"));
    }

    @Test
    public void getFromAlbumOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums/{albumId}/audio", 40)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(40));
    }

    @Test
    public void createPlaylist() throws Exception {
        PlaylistCreateDto playlistCreateDto = PlaylistCreateDto.builder()
                .image("MyImage")
                .name("MyName")
                .build();

        mockMvc.perform(post(apiUrl + "/user/{userId}/playlists", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(playlistCreateDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.image").value("MyImage"))
                .andExpect(jsonPath("$.name").value("MyName"));

        mockMvc.perform(post(apiUrl + "/user/{userId}/playlists", 900)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(playlistCreateDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The user does not exist"));

        mockMvc.perform(post(apiUrl + "/user/{userId}/playlists", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(playlistCreateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("The playlist already exists"));
    }

    @Test
    public void deletePlaylist() throws Exception {
        mockMvc.perform(delete(apiUrl + "/user/{userId}/playlists/{playlistId}", 2, 100))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("No playlist with id 100 for user 2"));

        mockMvc.perform(delete(apiUrl + "/user/{userId}/playlists/{playlistId}", 2, 10))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Playlist with id 10 deleted"));
    }

    @Test
    public void getPlaylistsOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/playlists", 2)
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].name").value("test0"));
    }

    @Test
    public void getPlaylistById() throws Exception {
        mockMvc.perform(get(apiUrl + "/playlists/{playlistId}", 100))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$.name").value("test0"));

        mockMvc.perform(get(apiUrl + "/playlists/{playlistId}", 900))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("No playlist with id 900"));
    }

    @Test
    public void addAudioToPlaylist() throws Exception {
        mockMvc.perform(put(apiUrl + "/playlists/{playlistId}/audio", 200)
                .param("audioId", "40"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));

        mockMvc.perform(put(apiUrl + "/playlists/{playlistId}/audio", 200)
                .param("audioId", "500"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("No audio with id 500 found"));

        mockMvc.perform(put(apiUrl + "/playlists/{playlistId}/audio", 700)
                .param("audioId", "40"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("No playlist with id 700"));

        mockMvc.perform(put(apiUrl + "/playlists/{playlistId}/audio", 200)
                .param("audioId", "40"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("This playlist already contains audio with id 40"));

    }

    @Test
    public void removeAudioFromPlaylist() throws Exception {
        mockMvc.perform(delete(apiUrl + "/playlists/{playlistId}/audio/{audioId}", 100, 20))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAudioFromPlaylist() throws Exception {
        mockMvc.perform(get(apiUrl + "/playlists/{playlistId}/audio", 100)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3))
                .andExpect(jsonPath("$.items[0].id").value(20))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon1"));
    }
}