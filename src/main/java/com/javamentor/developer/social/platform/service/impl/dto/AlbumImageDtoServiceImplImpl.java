package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumImageDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AlbumImageDtoServiceImplImpl extends PaginationServiceImpl implements AlbumImageDtoService {
    public AlbumImageDtoServiceImplImpl() {
    }

    @Override
    @Transactional
    public PageDto<AlbumImageDto, ?> getAllByUserId(Map<String, Object> parameters) {
        //noinspection unchecked
        return super.getPageDto("getAllImageAlbumsOfUser", parameters);
    }

}
