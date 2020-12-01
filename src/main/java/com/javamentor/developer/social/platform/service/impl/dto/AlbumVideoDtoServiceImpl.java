package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumVideoDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AlbumVideoDtoServiceImpl extends PaginationServiceImpl<AlbumVideoDto, Object> implements AlbumVideoDtoService {

    public AlbumVideoDtoServiceImpl() {
    }

    @Override
    public PageDto<AlbumVideoDto, Object> getAllByUserId(Map<String, Object> parameters) {
        return super.getPageDto("getAllVideoAlbums", parameters);
    }

}
