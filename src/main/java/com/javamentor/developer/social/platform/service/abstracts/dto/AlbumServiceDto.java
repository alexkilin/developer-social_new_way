package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AlbumDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AlbumServiceDto {

    List<AlbumDto> getAlbumOfUser(Long id);

    void createAlbum(String name, String icon,  Long userId);
}
