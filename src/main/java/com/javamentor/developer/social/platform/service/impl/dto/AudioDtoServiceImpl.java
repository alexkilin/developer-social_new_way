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
    public PageDto<AudioDto, Object> getAudioOfAuthor(Map<String, Object> parameters) {
        return super.getPageDto("getAudioOfAuthor", parameters);
    }

    @Override
    public PageDto<AudioDto, Object> getAudioOfName(Map<String, Object> parameters) {
        return super.getPageDto("getAudioOfName", parameters);
    }

    @Override
    public PageDto<AudioDto, Object> getAudioOfAlbum(Map<String, Object> parameters) {
        return super.getPageDto("getAudioOfAlbum", parameters);
    }

    @Override
    public PageDto<AudioDto, Object> getPartAudioOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getPartAudioOfUser", parameters);
    }

    @Override
    public PageDto<AudioDto, Object> getAuthorAudioOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getAuthorAudioOfUser", parameters);
    }

    @Override
    public PageDto<AudioDto, Object> getAlbumAudioOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getAlbumAudioOfUser", parameters);
    }

    @Override
    public PageDto<AudioDto, Object> getAudioFromAlbumOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getFromAlbumOfUser", parameters);
    }

    @Override
    public PageDto<AudioDto, Object> getAudioFromPlaylist(Map<String, Object> parameters) {
        return super.getPageDto("getAudioFromPlaylistById", parameters);
    }

    @Override
    public PageDto<AudioDto, Object> getPartAudio(Map<String, Object> parameters) {
        return super.getPageDto("getPartAudios", parameters);
    }

    @Override
    public PageDto<AudioDto, ?> getPartAudiosOfFriends(Map<String, Object> parameters) {
        return super.getPageDto("getPartAudiosOfFriends", parameters);
    }

    @Override
    public PageDto<AudioDto, ?> getAudioSortedByListening(Map<String, Object> parameters) {
        return super.getPageDto("getAudioSortedByListening", parameters);
    }

    @Override
    public PageDto<AudioDto, ?> getAudioByGenre(Map<String, Object> parameters) {
        return super.getPageDto("getAudioByGenre", parameters);
    }

}
