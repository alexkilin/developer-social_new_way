package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumImageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumImageDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.page.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AlbumImageDtoServiceImpl extends PaginationService implements AlbumImageDtoService {

    private final AlbumImageDtoDao albumImageDtoDao;

    @Autowired
    public AlbumImageDtoServiceImpl(AlbumImageDtoDao albumImageDtoDao) {
        this.albumImageDtoDao = albumImageDtoDao;
    }

    @Override
    @Transactional
    public PageDto<AlbumImageDto, ?> getAllByUserId(Map<String, Object> parameters) {
        return super.getPageDto("getAllImageAlbumsOfUser", parameters);
    }

}
