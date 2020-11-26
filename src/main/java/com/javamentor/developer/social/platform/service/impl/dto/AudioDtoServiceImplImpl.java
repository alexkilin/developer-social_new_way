package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AudioDtoServiceImplImpl extends PaginationServiceImpl implements AudioDtoService {

    private final AudioDtoDao audioDtoDao;

    @Autowired
    public AudioDtoServiceImplImpl(AudioDtoDao audioDtoDao) {
        this.audioDtoDao = audioDtoDao;
    }

    @Override
    @Transactional
    public PageDto<AudioDto, ?> getAudioOfAuthor(Map<String, Object> parameters) {
        return super.getPageDto("getAudioOfAuthor", parameters);
    }

    @Override
    @Transactional
    public PageDto<AudioDto, ?> getAudioOfName(Map<String, Object> parameters) {
        return super.getPageDto("getAudioOfName", parameters);
    }

    @Override
    @Transactional
    public PageDto<AudioDto, ?> getAudioOfAlbum(Map<String, Object> parameters) {
        return super.getPageDto("getAudioOfAlbum", parameters);
    }

    @Override
    @Transactional
    public PageDto<AudioDto, ?> getPartAudioOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getPartAudioOfUser", parameters);
    }

    @Override
    @Transactional
    public PageDto<AudioDto, ?> getAuthorAudioOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getAuthorAudioOfUser", parameters);
    }

    @Override
    @Transactional
    public PageDto<AudioDto, ?> getAlbumAudioOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getAlbumAudioOfUser", parameters);
    }

    @Override
    @Transactional
    public PageDto<AudioDto, ?> getAudioFromAlbumOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getFromAlbumOfUser", parameters);
    }

    @Override
    @Transactional
    public PageDto<AudioDto, ?> getAudioFromPlaylist(Map<String, Object> parameters) {
        return super.getPageDto("getAudioFromPlaylistById", parameters);
    }

    @Override
    @Transactional
    public PageDto<AudioDto, ?> getPartAudio(Map<String, Object> parameters) {
        return super.getPageDto("getPartAudios", parameters);
    }

}
