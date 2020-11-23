package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AudioDtoServiceImpl implements AudioDtoService {

    private final AudioDtoDao audioDtoDao;

    @Autowired
    public AudioDtoServiceImpl(AudioDtoDao audioDtoDao) {
        this.audioDtoDao = audioDtoDao;
    }

    @Override
    public List<AudioDto> getAudioOfAuthor(String author, int currentPage, int itemsOnPage) {

        return audioDtoDao.getAudioOfAuthor(author, currentPage, itemsOnPage);
    }

    @Override
    public List<AudioDto> getAudioOfName(String name, int currentPage, int itemsOnPage) {
        return audioDtoDao.getAudioOfName(name, currentPage, itemsOnPage);
    }

    @Override
    public List<AudioDto> getAudioOfAlbum(String album, int currentPage, int itemsOnPage) {
        return audioDtoDao.getAudioOfAlbum(album, currentPage, itemsOnPage);
    }

    @Override
    public List<AudioDto> getPartAudioOfUser(Long userId, int currentPage, int itemsOnPage) {
        return audioDtoDao.getPartAudioOfUser(userId,currentPage,itemsOnPage);
    }

    @Override
    public List<AudioDto> getAuthorAudioOfUser(Long userId, String author, int currentPage, int itemsOnPage) {
        return audioDtoDao.getAuthorAudioOfUser(userId, author, currentPage, itemsOnPage);
    }

    @Override
    public List<AudioDto> getAlbumAudioOfUser(Long userId, String album, int currentPage, int itemsOnPage) {
        return audioDtoDao.getAlbumAudioOfUser(userId, album, currentPage, itemsOnPage);
    }

    @Override
    public List<AudioDto> getAudioFromAlbumOfUser(Long albumId, int currentPage, int itemsOnPage) {
        return audioDtoDao.getAudioFromAlbumOfUser(albumId, currentPage, itemsOnPage);
    }

    @Override
    public List<AudioDto> getAudioFromPlaylist(Long playlistId, int currentPage, int itemsOnPage) {
        return audioDtoDao.getAudioFromPlaylist(playlistId, currentPage, itemsOnPage);
    }

    @Override
    public List<AudioDto> getPartAudio(int currentPage, int itemsOnPage) {
        return audioDtoDao.getPartAudio(currentPage, itemsOnPage);
    }

}
