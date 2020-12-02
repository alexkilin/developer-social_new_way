package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.entity.media.Audios;

public interface AudioDtoDao {

    Audios getAudioOfId(Long id);
}
