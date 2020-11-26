package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.VideoDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.VideoDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class VideoDtoServiceImplImpl extends PaginationServiceImpl implements VideoDtoService {
    private final VideoDtoDao videoDtoDao;

    public VideoDtoServiceImplImpl(VideoDtoDao videoDtoDao) {
        this.videoDtoDao = videoDtoDao;
    }

    @Override
    @Transactional
    public PageDto<VideoDto, ?> getPartVideo(Map<String, Object> parameters) {
        return super.getPageDto("getPartVideos", parameters);
    }

    @Override
    @Transactional
    public PageDto<VideoDto, ?> getVideoOfAuthor(Map<String, Object> parameters) {
        return super.getPageDto("getVideoOfAuthor", parameters);
    }

    @Override
    @Transactional
    public VideoDto getVideoOfName(String name) {
        return videoDtoDao.getVideoOfName(name).orElseThrow(() -> new IllegalArgumentException("Invalid parameters"));
    }

    @Override
    @Transactional
    public PageDto<VideoDto, ?> getPartVideoOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getPartVideoOfUser", parameters);
    }

    @Override
    @Transactional
    public PageDto<VideoDto, ?> getAuthorVideoOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getAuthorVideoOfUser", parameters);
    }

    @Override
    @Transactional
    public PageDto<VideoDto, ?> getAlbumVideoOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getAlbumVideoOfUser", parameters);
    }

    @Override
    @Transactional
    public PageDto<VideoDto, ?> getVideoFromAlbumOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getVideoFromAlbumOfUser", parameters);
    }
}
