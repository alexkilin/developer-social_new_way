package com.javamentor.developer.social.platform.restv2.controllers;


import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "datasets/restv2/user/userFriends.yml",
        "datasets/restv2/user/Active.yml",
        "datasets/restv2/user/User.yml",
        "datasets/restv2/user/Role.yml",
        "datasets/restv2/audio/UsersAudiosCollections.yml",
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

    @Test
    public void getAllAudioPaged() throws Exception {
        this.mockMvc.perform(get(apiUrl + "/")
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(4))
                .andExpect(jsonPath("$.items[0].id").value(1))
                .andExpect(jsonPath("$.items[0].url").value("www.myaudio1.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon0"))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 0"))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 0"))
                .andExpect(jsonPath("$.items[0].album").value("AlbumTestName 0"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[1].id").value(2))
                .andExpect(jsonPath("$.items[2].id").value(3))
                .andExpect(jsonPath("$.items[3].id").value(4));
    }
}
