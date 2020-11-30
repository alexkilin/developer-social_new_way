package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PlaylistDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.PlaylistDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PlaylistPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlaylistDtoServiceImpl extends PlaylistPaginationService<PlaylistGetDto, Object> implements PlaylistDtoService {

    private final PlaylistDtoDao playlistDtoDao;

    @Autowired
    public PlaylistDtoServiceImpl(PlaylistDtoDao playlistDtoDao) {
        this.playlistDtoDao = playlistDtoDao;
    }

    @Override
    public Optional<PlaylistGetDto> getById(Long id) {
        Optional<PlaylistGetDto> optional = playlistDtoDao.getById(id);
        if (optional.isPresent()) {
            PlaylistGetDto playlistGetDto = optional.get();
            List<Long> playlistId = new ArrayList<>();
            playlistId.add(playlistGetDto.getId());
            playlistGetDto.setContent(playlistDtoDao.getAudioDtoByPlaylistId(playlistId));
            optional = Optional.of(playlistGetDto);
        }
        return optional;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<PlaylistGetDto, Object> getAllByUserId(Map<String, Object> parameters) {
        return (PageDto<PlaylistGetDto, Object>) super.getPlaylistPageDto("getPlaylistsOfUser", parameters);
    }
}
