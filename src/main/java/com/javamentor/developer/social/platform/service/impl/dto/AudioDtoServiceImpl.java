package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AudioDtoServiceImpl implements AudioDtoService {

    private final AudioDtoDao audioDtoDao;

    @Autowired
    public AudioDtoServiceImpl(AudioDtoDao audioDtoDao) {
        this.audioDtoDao = audioDtoDao;
    }

    @Override
    @Transactional
    public List<AudioDto> getAudioOfAuthor(String author) {
        return audioDtoDao.getAudioOfAuthor(author);
    }

    @Override
    @Transactional
    public List<AudioDto> getAudioOfName(String name) {
        return audioDtoDao.getAudioOfName(name);
    }

    @Override
    @Transactional
    public List<AudioDto> getAudioOfAlbum(String album) {
        return audioDtoDao.getAudioOfAlbum(album);
    }

    @Override
    @Transactional
    public List<AudioDto> getAudioOfUser(Long userId) {
        return audioDtoDao.getAudioOfUser(userId);
    }

    @Override
    @Transactional
    public List<AudioDto> getPartAudioOfUser(Long userId, int currentPage, int itemsOnPage) {
        return audioDtoDao.getPartAudioOfUser(userId,currentPage,itemsOnPage);
    }

    @Override
    @Transactional
    public List<AudioDto> getAuthorAudioOfUser(Long userId, String author) {
        return audioDtoDao.getAuthorAudioOfUser(userId, author);
    }

    @Override
    @Transactional
    public List<AudioDto> getAlbumAudioOfUser(Long userId, String album) {
        return audioDtoDao.getAlbumAudioOfUser(userId, album);
    }

    @Override
    @Transactional
    public List<AudioDto> getAudioFromAlbumOfUser(Long albumId) {
        return audioDtoDao.getAudioFromAlbumOfUser(albumId);
    }

    @Override
    @Transactional
    public List<AudioDto> getAudioFromPlaylist(Long playlistId, int offset, int limit) {
        return audioDtoDao.getAudioFromPlaylist(playlistId, offset, limit);
    }

    @Override
    @Transactional
    public List<AudioDto> getPartAudio(int currentPage, int itemsOnPage) {
        return audioDtoDao.getPartAudio(currentPage, itemsOnPage);
    }

}
