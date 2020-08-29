package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumServiceDto;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceDtoImpl implements AlbumServiceDto {

    private final AlbumDtoDao albumDtoDao;
    private final UserService userService;

    @Autowired
    public AlbumServiceDtoImpl(AlbumDtoDao albumDtoDao, UserService userService) {
        this.albumDtoDao = albumDtoDao;
        this.userService = userService;
    }

    @Override
    public List<AlbumDto> getAlbumOfUser(Long id) {
        return albumDtoDao.getAlbumOfUser(id);
    }

    @Override
    public void createAlbum(String name, String icon, Long userId) {
        albumDtoDao.createAlbum(name, icon, userId);
    }

    @Override
    public AlbumDto createAlbum(AlbumDto albumDto, Long userOwnerId) {
        return albumDtoDao.createAlbum(albumDto, userService.getById(userOwnerId));
    }
}
