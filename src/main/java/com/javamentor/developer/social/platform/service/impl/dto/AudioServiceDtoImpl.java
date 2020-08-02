package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class AudioServiceDtoImpl implements AudioServiceDto{

    private final AudioDtoDao audioDtoDao;

    @Autowired
    public AudioServiceDtoImpl(AudioDtoDao audioDtoDao) {
        this.audioDtoDao = audioDtoDao;
    }

    @Override
    public Optional<List<AudioDto>> getAllAudios() {
        return audioDtoDao.getAllAudios();
    }

    @Override
    public Optional<List<AudioDto>> getPartAudio(int currentPage, int itemsOnPage) {
            return audioDtoDao.getPartAudio(currentPage, itemsOnPage);
    }

    @Override
    public Optional<List<AudioDto>> getAudioOfAuthor(String author) {
        return audioDtoDao.getAudioOfAuthor(author);
    }

    @Override
    public Optional<AudioDto> getAudioOfName(String name) {
        return audioDtoDao.getAudioOfName(name);
    }

    @Override
    public Optional<List<AudioDto>> getAudioOfAlbum(String album) {
        return audioDtoDao.getAudioOfAlbum(album);
    }

    @Override
    public Optional<List<AudioDto>> getAudioOfUser(Long userId) {
        return audioDtoDao.getAudioOfUser(userId);
    }

    @Override
    public Optional<List<AudioDto>> getPartAudioOfUser(Long userId, int currentPage, int itemsOnPage) {
        return audioDtoDao.getPartAudioOfUser(userId,currentPage,itemsOnPage);
    }

    @Override
    public Optional<List<AudioDto>> getAuthorAudioOfUser(Long userId, String author) {
        return audioDtoDao.getAuthorAudioOfUser(userId, author);
    }

    @Override
    public Optional<List<AudioDto>> getAlbumAudioOfUser(Long userId, String album) {
        return audioDtoDao.getAlbumAudioOfUser(userId, album);
    }

    @Override
    public boolean addAudioInCollectionsOfUser(Long userId, Long audioId) {
        return audioDtoDao.addAudioInCollectionsOfUser(userId, audioId);
    }
}
