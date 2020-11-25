package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PlaylistDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.PlaylistDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.page.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class PlaylistDtoServiceImpl extends PaginationService implements PlaylistDtoService {

    private final PlaylistDtoDao playlistDtoDao;

    @Autowired
    public PlaylistDtoServiceImpl(PlaylistDtoDao playlistDtoDao) {
        this.playlistDtoDao = playlistDtoDao;
    }

    @Override
    @Transactional
    public Optional<PlaylistGetDto> getById(Long id) {
        Optional<PlaylistGetDto> optional = playlistDtoDao.getById(id);
        if (optional.isPresent()) {
            PlaylistGetDto playlistGetDto = optional.get();
            playlistGetDto.setContent(playlistDtoDao.getAudioDtoByPlaylistId(playlistGetDto.getId()));
            optional = Optional.of(playlistGetDto);
        }
        return optional;
    }

    @Override
    @Transactional
    public PageDto<PlaylistGetDto, ?> getAllByUserId(Map<String, Object> parameters) {
        return super.getPageDto("getPlaylistsOfUser", parameters);
    }
}
