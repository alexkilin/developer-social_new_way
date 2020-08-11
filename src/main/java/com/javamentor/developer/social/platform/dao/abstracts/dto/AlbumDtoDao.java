package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.AudioDto;

import java.util.List;

public interface AlbumDtoDao {

    List<AlbumDto> getAlbumOfUser(Long id);

    void createAlbum(String name, String icon, Long userId);
}
