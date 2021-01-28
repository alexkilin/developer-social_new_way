package com.javamentor.developer.social.platform.restv2.controllers;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistCreateDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "datasets/restv2/audio/usersResources/Active.yml",
        "datasets/restv2/audio/usersResources/User.yml",
        "datasets/restv2/audio/usersResources/Role.yml",
        "datasets/restv2/audio/audioResources/UsersAudiosCollections.yml" ,
        "datasets/restv2/audio/playlistAudioTest/Playlist.yml",
        "datasets/restv2/audio/playlistAudioTest/PlaylistHasAudios.yml",
        "datasets/restv2/audio/audioResources/Media.yml" ,
        "datasets/restv2/audio/albumAudioTest/AudioAlbum.yml",
        "datasets/restv2/audio/albumAudioTest/Album.yml",
        "datasets/restv2/audio/albumAudioTest/UserHasAlbum.yml",
        "datasets/restv2/audio/albumAudioTest/AlbumHasAudio.yml",
        "datasets/restv2/audio/audioResources/Audio.yml"}, strategy = SeedStrategy.REFRESH, cleanAfter = true)
@Sql(value = "/create_user_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
class AudiosControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/audio";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    Gson gson = new Gson();

    @Test
    public void getPartAudios() throws Exception {
        this.mockMvc.perform(get(apiUrl + "/")
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(4))
                .andExpect(jsonPath("$.items[0].id").value(200))
                .andExpect(jsonPath("$.items[0].url").value("www.myaudio7.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon7"))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 7"))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 7"))
                .andExpect(jsonPath("$.items[0].album").value("AlbumTestName 7"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[1].id").value(201))
                .andExpect(jsonPath("$.items[2].id").value(202))
                .andExpect(jsonPath("$.items[3].id").value(203));
    }

    @Test
    public void getAudioOfAuthor() throws Exception {
        mockMvc.perform(get(apiUrl + "/author/{author}", "Test Author 1")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(201))
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(202))
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(205))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 2"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 5"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon5"));
    }

    @Test
    public void getPartAudioOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}", 200)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(4))
                .andExpect(jsonPath("$.items[0].id").value(200))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 7"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 7"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon7"))
                .andExpect(jsonPath("$.items[1].id").value(201))
                .andExpect(jsonPath("$.items[1].author").value("Test Author 1"))
                .andExpect(jsonPath("$.items[1].length").value(365))
                .andExpect(jsonPath("$.items[1].name").value("AudioTestName 1"))
                .andExpect(jsonPath("$.items[1].icon").value("TestIcon1"));
    }

    @Test
    public void getAuthorAudioOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/author?author=Test Author 1", 202)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(201))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 1"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 1"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon1"));
    }

    @Test
    public void getAlbumAudioOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/album?album=AlbumTestName 7", 205)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(200))
                .andExpect(jsonPath("$.items[0].author").value("Test Author 7"))
                .andExpect(jsonPath("$.items[0].length").value(365))
                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 7"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon7"));
    }

    @Test
    public void addAudioInCollectionsOfUser() throws Exception {

       mockMvc.perform(put(apiUrl + "/user/audio")
                .param("audioId", "200"))
                .andExpect(status().isOk())
                .andExpect(content().string("Audio id 200 added to collection of user id 666"));

        User user = (User) entityManager.createQuery("SELECT u from User as u join fetch u.audios a where u.userId = 666")
                .getSingleResult();
        assertEquals(1, user.getAudios().size());

        mockMvc.perform(put(apiUrl + "/user/audio")
                .param("audioId", "1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Audio with id 1000 not found"));
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


        mockMvc.perform(post(apiUrl + "/user/{userId}/audio", 200)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(audioDto)))
                .andExpect(status().isCreated());

        Audios audios = (Audios) entityManager.createQuery("SELECT a from Audios a where a.length = 200")
                .getSingleResult();
        assertEquals("Test Author 200", audios.getAuthor());

        mockMvc.perform(post(apiUrl + "/user/{userId}/audio", 1000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(audioDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User id 1000 not found"));
    }

    @Test
    public void getAllAlbums() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/album", 200)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3))
                .andExpect(jsonPath("$.items[0].id").value(200))
                .andExpect(jsonPath("$.items[0].name").value("audioAlbum"))
                .andExpect(jsonPath("$.items[0].icon").value("icon 2"))
                .andExpect(jsonPath("$.items[1].id").value(201))
                .andExpect(jsonPath("$.items[1].name").value("audioAlbum"))
                .andExpect(jsonPath("$.items[1].icon").value("icon 3"));
    }

    @Test
    public void addInAlbums() throws Exception {
        mockMvc.perform(put(apiUrl + "/albums/{albumId}/audio", 200)
                .param("audioId", "205"))
                .andExpect(status().isOk())
                .andExpect(content().string("Audio id 205 added to album id 200"));

        AlbumAudios albumAudios = (AlbumAudios) entityManager.createQuery("SELECT a from AlbumAudios a join fetch a.audios where a.id = 200")
                .getSingleResult();
        Set<Audios> audiosSet = albumAudios.getAudios();
        assertEquals(3,audiosSet.size());
        Optional<Audios> audios200 = albumAudios.getAudios().stream().filter(a -> a.getId() == 200).findFirst();
        Optional<Audios> audios201 = albumAudios.getAudios().stream().filter(a -> a.getId() == 201).findFirst();
        Optional<Audios> audios205 = albumAudios.getAudios().stream().filter(a -> a.getId() == 205).findFirst();
        assertTrue(audios200.isPresent());
        assertTrue(audios201.isPresent());
        assertTrue(audios205.isPresent());


        mockMvc.perform(put(apiUrl + "/albums/{albumId}/audio", 1000)
                .param("audioId", "200"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album id 1000 not found"));

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/audio", 200)
                .param("audioId", "1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Audio id 1000 not found"));
    }

    @Test
    public void createAlbum() throws Exception {
        AlbumDto albumDto = AlbumDto.builder()
                .icon("TestIcon991")
                .name("MyTestAlbum")
                .build();

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 200)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MyTestAlbum"));

        AlbumAudios album = (AlbumAudios) entityManager.createQuery("SELECT a from AlbumAudios a join fetch a.album a2 where a2.icon like :icon")
                .setParameter("icon", "TestIcon991")
                .getSingleResult();
        assertEquals("MyTestAlbum", album.getAlbum().getName());
        assertEquals(com.javamentor.developer.social.platform.models.entity.media.MediaType.AUDIO, album.getAlbum().getMediaType());

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 200)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Audio album with name 'MyTestAlbum' already exists"));
    }

    @Test
    public void getFromAlbumOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums/{albumId}/audio", 201)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(202));
    }

    @Test
    public void createPlaylist() throws Exception {
        PlaylistCreateDto playlistCreateDto = PlaylistCreateDto.builder()
                .image("MyImageTest")
                .name("MyNameTest")
                .build();

        mockMvc.perform(post(apiUrl + "/user/{userId}/playlists", 200)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(playlistCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.image").value("MyImageTest"))
                .andExpect(jsonPath("$.name").value("MyNameTest"));

        Playlist playlist = (Playlist) entityManager.createQuery("SELECT a from Playlist a where a.name like :name")
                .setParameter("name", "MyNameTest")
                .getSingleResult();
        assertEquals("MyImageTest", playlist.getImage());

        mockMvc.perform(post(apiUrl + "/user/{userId}/playlists", 1000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(playlistCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The user does not exist"));

        mockMvc.perform(post(apiUrl + "/user/{userId}/playlists", 200)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(playlistCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("The playlist already exists"));
    }

    @Test
    public void deletePlaylist() throws Exception {
        mockMvc.perform(delete(apiUrl + "/user/{userId}/playlists/{playlistId}", 200, 1000))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No playlist with id 1000 for user 200"));

        mockMvc.perform(delete(apiUrl + "/user/{userId}/playlists/{playlistId}", 200, 200))
                .andExpect(status().isOk())
                .andExpect(content().string("Playlist with id 200 deleted"));
    }

    @Test
    public void getPlaylistsOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/playlists", 201)
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].name").value("test1"));
    }

    @Test
    public void getPlaylistById() throws Exception {
        mockMvc.perform(get(apiUrl + "/playlists/{playlistId}", 200))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$.name").value("test0"));

        mockMvc.perform(get(apiUrl + "/playlists/{playlistId}", 1000))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No playlist with id 1000"));
    }

    @Test
    public void addAudioToPlaylist() throws Exception {
        mockMvc.perform(put(apiUrl + "/playlists/{playlistId}/audio", 200)
                .param("audioId", "203"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(4));

        Playlist playlist = (Playlist) entityManager.createQuery("SELECT a from Playlist a join fetch a.playlistContent where a.id =200")
                .getSingleResult();
        Set <Audios> audiosSet =  playlist.getPlaylistContent();
        assertEquals(4, audiosSet.size());

        mockMvc.perform(put(apiUrl + "/playlists/{playlistId}/audio", 200)
                .param("audioId", "204"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(5));

        Playlist playlist2 = (Playlist) entityManager.createQuery("SELECT a from Playlist a join fetch a.playlistContent where a.id =200")
                .getSingleResult();
        Set <Audios> audiosSet2 =  playlist2.getPlaylistContent();
        assertEquals(5, audiosSet2.size());

        mockMvc.perform(put(apiUrl + "/playlists/{playlistId}/audio", 200)
                .param("audioId", "1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No audio with id 1000 found"));

        mockMvc.perform(put(apiUrl + "/playlists/{playlistId}/audio", 1000)
                .param("audioId", "200"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No playlist with id 1000"));

        mockMvc.perform(put(apiUrl + "/playlists/{playlistId}/audio", 200)
                .param("audioId", "203"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("This playlist already contains audio with id 203"));

    }

    @Test
    public void removeAudioFromPlaylist() throws Exception {
        mockMvc.perform(delete(apiUrl + "/playlists/{playlistId}/audio/{audioId}", 200, 200))
                .andExpect(status().isOk());
    }

    @Test
    public void getAudioFromPlaylist() throws Exception {
        mockMvc.perform(get(apiUrl + "/playlists/{playlistId}/audio", 200)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3))
                .andExpect(jsonPath("$.items[0].id").value(200))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon7"));
    }
}