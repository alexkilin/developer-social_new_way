package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumDtoDao;
import com.javamentor.developer.social.platform.models.dto.AlbumCreateDto;
import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumDtoServiceImpl implements AlbumDtoService {

    private final AlbumDtoDao albumDtoDao;
    private final UserService userService;
    private final AlbumConverter albumConverter;
    private final AlbumImageService albumImageService;

    @Autowired
    public AlbumDtoServiceImpl(AlbumDtoDao albumDtoDao, UserService userService, AlbumConverter albumConverter, AlbumImageService albumImageService) {
        this.albumDtoDao = albumDtoDao;
        this.userService = userService;
        this.albumConverter = albumConverter;
        this.albumImageService = albumImageService;
    }

    @Override
    public List<AlbumDto> getAllByTypeAndUserId(MediaType type, Long userId) {
        return albumDtoDao.getAllByTypeAndUserId(type, userId);
    }

    @Override
    public AlbumDto createAlbumImage(AlbumCreateDto albumCreateDto) {
        AlbumImage newAlbumImage = albumConverter.toAlbumImage(albumCreateDto);
        albumImageService.create(newAlbumImage);
        return albumConverter.toAlbumDto(newAlbumImage);
    }

    @Override
    public Optional<AlbumDto> getById(Long id) {
        return albumDtoDao.getById(id);
    }


}
