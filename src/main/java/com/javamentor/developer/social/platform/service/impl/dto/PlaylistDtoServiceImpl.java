package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PlaylistDtoDao;
import com.javamentor.developer.social.platform.models.dto.PlaylistCreateDto;
import com.javamentor.developer.social.platform.models.dto.PlaylistGetDto;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import com.javamentor.developer.social.platform.service.abstracts.dto.PlaylistDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.PlaylistService;
import com.javamentor.developer.social.platform.webapp.converters.PlaylistConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistDtoServiceImpl implements PlaylistDtoService {

    private PlaylistDtoDao playlistDtoDao;
    private PlaylistService playlistService;
    private PlaylistConverter playlistConverter;

    @Autowired
    public PlaylistDtoServiceImpl(PlaylistDtoDao playlistDtoDao,
                                  PlaylistService playlistService,
                                  PlaylistConverter playlistConverter) {
        this.playlistDtoDao = playlistDtoDao;
        this.playlistService = playlistService;
        this.playlistConverter = playlistConverter;
    }

    @Override
    public PlaylistGetDto getById(Long id) {
        PlaylistGetDto playlistGetDto = playlistDtoDao.getById(id);
        playlistGetDto.setContent(playlistDtoDao.getAudioDtoByPlaylistId(playlistGetDto.getId()));
        return playlistGetDto;
    }

    @Override
    public List<PlaylistGetDto> getAllByUserId(Long userId) {
        List<PlaylistGetDto> playlistGetDtoList = playlistDtoDao.getByUserId(userId);
        playlistGetDtoList.forEach(playlist -> {
            Long id = playlist.getId();
            playlist.setContent(playlistDtoDao.getAudioDtoByPlaylistId(id));
        });
        return playlistGetDtoList;
    }

    @Override
    public PlaylistGetDto create(PlaylistCreateDto playlistCreateDto) {
        Playlist newPlaylist = playlistConverter.toEntity(playlistCreateDto);
        playlistService.create(newPlaylist);
        return playlistConverter.toPlaylistGetDto(newPlaylist);
    }
}
