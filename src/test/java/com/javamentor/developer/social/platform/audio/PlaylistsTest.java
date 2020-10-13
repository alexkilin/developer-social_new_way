package com.javamentor.developer.social.platform.audio;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import com.javamentor.developer.social.platform.models.dto.PlaylistCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataSet(value = {
        "datasets/audio/usersAudioTest/Active.yml",
        "datasets/audio/usersAudioTest/User.yml",
        "datasets/audio/usersAudioTest/Role.yml",
        "datasets/audio/usersAudioTest/Status.yml",
        "datasets/audio/usersAudioTest/UsersAudiosCollections.yml",
        "datasets/audio/Media.yml",
        "datasets/audio/albumAudioTest/AudioAlbum.yml",
        "datasets/audio/albumAudioTest/Album.yml",
        "datasets/audio/albumAudioTest/UserHasAlbum.yml",
        "datasets/audio/albumAudioTest/AlbumHasAudio.yml",
        "datasets/audio/Audio.yml",
        "datasets/audio/playlistAudioTest/Playlist.yml",
        "datasets/audio/playlistAudioTest/PlaylistHasAudios.yml"}, cleanBefore = true, cleanAfter = true)
class PlaylistsTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private Gson gson = new Gson();

    @Test
    void createNewPlaylist() throws Exception {
        PlaylistCreateDto dto = PlaylistCreateDto.builder()
                .name("albumName")
                .image("albumImage")
                .build();

        this.mockMvc.perform(post("/api/audios/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("name").value("albumName"))
                .andExpect(jsonPath("image").value("albumImage"));
    }

    @Test
    void createAndDelete() throws Exception {

        this.mockMvc.perform(delete("/api/audios/playlists/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Playlist with id %s deleted", 10)));

        this.mockMvc.perform(delete("/api/audios/playlists/10"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(String.format("No playlist with id %s for current user", 10)));
    }


    @Test
    void getAll() throws Exception {
        this.mockMvc.perform(get("/api/audios/playlists/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void getOneById() throws Exception {
        this.mockMvc.perform(get("/api/audios/playlists/12"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test2"))
                .andExpect(jsonPath("$.image").value("testimage2"))
                .andExpect(jsonPath("$.content[1].id").value("4"))
                .andExpect(jsonPath("$.content[1].url").value("www.myaudio4.ru"))
                .andExpect(jsonPath("$.content[1].icon").value("TestIcon3"))
                .andExpect(jsonPath("$.content[1].name").value("AudioTestName 3"))
                .andExpect(jsonPath("$.content[1].author").value("Test Author 3"))
                .andExpect(jsonPath("$.content[1].album").value("AlbumTestName 3"))
                .andExpect(jsonPath("$.content[1].persistDateTime").isNotEmpty());
    }
// playlists?playlistId=10&audioId=4
    // playlists?playlistId=10&audioId=4
    @Test
    void addAudioToPlaylist() throws Exception {

        this.mockMvc.perform(put("/api/audios/audio/playlists/10/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test0"))
                .andExpect(jsonPath("$.image").value("testimage0"))
                .andExpect(jsonPath("$.content.length()").value("4"));

        this.mockMvc.perform(put("/api/audios/audio/playlists/10/4"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteAudioFromPlaylist() throws Exception {
        this.mockMvc.perform(delete("/api/audios/audio/playlists/?playlistId=10&audioId=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value("2"));

        this.mockMvc.perform(delete("/api/audios/audio/playlists?playlistId=10&audioId=3" +
                ""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
