package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioLastPlayedDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioLastPlayedDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


    @Service
    public class AudioLastPlayedDtoServiceImpl implements AudioLastPlayedDtoService {
        private final  AudioDtoDao audioDtoDao;

        @Autowired
        public AudioLastPlayedDtoServiceImpl (AudioDtoDao audioDtoDao) {
            this.audioDtoDao=audioDtoDao;
        }


        @Override
        @Transactional
        public Optional<AudioLastPlayedDto> getAudioLastPlayedOfUserById(Long id) {
            return audioDtoDao. getLastPlayedAudioDtoByUserId(id);
        }

    }

