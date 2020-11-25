package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumVideoDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumVideoDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.page.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AlbumVideoDtoServiceImpl extends PaginationService implements AlbumVideoDtoService {

    private final AlbumVideoDtoDao albumVideoDtoDao;

    @Autowired
    public AlbumVideoDtoServiceImpl(AlbumVideoDtoDao albumVideoDtoDao) {
        this.albumVideoDtoDao = albumVideoDtoDao;
    }

    @Override
    @Transactional
    public PageDto<AlbumVideoDto, ?> getAllByUserId(Map<String, Object> parameters) {
        return super.getPageDto("getAllVideoAlbums", parameters);
    }

}
