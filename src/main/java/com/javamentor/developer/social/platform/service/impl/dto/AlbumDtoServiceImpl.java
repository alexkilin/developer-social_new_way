package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumDtoDao;
import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumDtoServiceImpl implements AlbumDtoService {

    private final AlbumDtoDao albumDtoDao;
    private final UserService userService;

    @Autowired
    public AlbumDtoServiceImpl(AlbumDtoDao albumDtoDao, UserService userService) {
        this.albumDtoDao = albumDtoDao;
        this.userService = userService;
    }

    @Override
    public List<AlbumDto> getAlbumOfUser(Long id) {
        return albumDtoDao.getAlbumOfUser(id);
    }

}
