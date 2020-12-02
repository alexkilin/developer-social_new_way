package com.javamentor.developer.social.platform.service.impl.dto.pagination;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PlaylistDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlaylistPaginationService<T, V> extends PaginationServiceImpl<T, V> {

    private PlaylistDtoDao playlistDtoDao;

    @Autowired
    public void setPlaylistDtoDao(PlaylistDtoDao playlistDtoDao) {
        this.playlistDtoDao = playlistDtoDao;
    }

    public PageDto<? extends T, ? extends V> getPlaylistPageDto(String methodName, Map<String, Object> parameters) {
        PageDto<PlaylistGetDto, ?> pageDto;
            pageDto = (PageDto<PlaylistGetDto, ?>) super.getPageDto(methodName, parameters);
        addContent(pageDto);

        return (PageDto<? extends T, ? extends V>) pageDto;
    }

    public void addContent(PageDto<PlaylistGetDto, ?> pageDto) {
        List<PlaylistGetDto> getDtoList = pageDto.getItems();
        List<Long> playlistIdList = getDtoList.stream().map(PlaylistGetDto::getId).collect(Collectors.toList());
        List<AudioDto> audiosForPlaylist = playlistDtoDao.getAudioDtoByPlaylistId(playlistIdList);

        Map<Long, List<AudioDto>> playlistContent = new HashMap<>();
        audiosForPlaylist.forEach(audioDto -> {
            if (audioDto != null) {
                if (!playlistContent.containsKey(audioDto.getPlaylistId())) {
                    playlistContent.put(audioDto.getPlaylistId(), new ArrayList<>());
                }
                playlistContent.get(audioDto.getPlaylistId()).add(audioDto);
            }
        });

        getDtoList.forEach(playlistGetDto -> {
            playlistGetDto.setContent(playlistContent.get(playlistGetDto.getId()));
            if (playlistGetDto.getContent() == null) {
                playlistGetDto.setContent(new ArrayList<>());
            }
        });
    }
}
