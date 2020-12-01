package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumImageDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AlbumImageDtoServiceImpl extends PaginationServiceImpl<AlbumImageDto, Object> implements AlbumImageDtoService {
    public AlbumImageDtoServiceImpl() {
    }

    @Override
    public PageDto<AlbumImageDto, Object> getAllByUserId(Map<String, Object> parameters) {
        return super.getPageDto("getAllImageAlbumsOfUser", parameters);
    }

}
