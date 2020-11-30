package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AudioDtoServiceImpl extends PaginationServiceImpl<AudioDto, Object> implements AudioDtoService {

    public AudioDtoServiceImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<AudioDto, Object> getAudioOfAuthor(Map<String, Object> parameters) {
        return (PageDto<AudioDto, Object>) super.getPageDto("getAudioOfAuthor", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<AudioDto, Object> getAudioOfName(Map<String, Object> parameters) {
        return (PageDto<AudioDto, Object>) super.getPageDto("getAudioOfName", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<AudioDto, Object> getAudioOfAlbum(Map<String, Object> parameters) {
        return (PageDto<AudioDto, Object>) super.getPageDto("getAudioOfAlbum", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<AudioDto, Object> getPartAudioOfUser(Map<String, Object> parameters) {
        return (PageDto<AudioDto, Object>) super.getPageDto("getPartAudioOfUser", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<AudioDto, Object> getAuthorAudioOfUser(Map<String, Object> parameters) {
        return (PageDto<AudioDto, Object>) super.getPageDto("getAuthorAudioOfUser", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<AudioDto, Object> getAlbumAudioOfUser(Map<String, Object> parameters) {
        return (PageDto<AudioDto, Object>) super.getPageDto("getAlbumAudioOfUser", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<AudioDto, Object> getAudioFromAlbumOfUser(Map<String, Object> parameters) {
        return (PageDto<AudioDto, Object>) super.getPageDto("getFromAlbumOfUser", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<AudioDto, Object> getAudioFromPlaylist(Map<String, Object> parameters) {
        return (PageDto<AudioDto, Object>) super.getPageDto("getAudioFromPlaylistById", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<AudioDto, Object> getPartAudio(Map<String, Object> parameters) {
        return (PageDto<AudioDto, Object>) super.getPageDto("getPartAudios", parameters);
    }

}
