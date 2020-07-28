package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AudioServiceDtoImpl implements AudioServiceDto{

    private final AudioDtoDao audioDtoDao;

    @Autowired
    public AudioServiceDtoImpl(AudioDtoDao audioDtoDao) {
        this.audioDtoDao = audioDtoDao;
    }

    @Override
    public List<AudioDto> getAllAudios() {
        return audioDtoDao.getAllAudios();
    }

    @Override
    public List<AudioDto> getPartAudio(int start, int end) {
        if (end-start>0) {
            return audioDtoDao.getPartAudio(start, end);
        }
        else return new ArrayList<>();
    }

    @Override
    public List<AudioDto> getAudioOfAuthor(String author) {
        return audioDtoDao.getAudioOfAuthor(author);
    }
}
