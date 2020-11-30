package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.VideoDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.VideoDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VideoDtoServiceImpl extends PaginationServiceImpl<VideoDto, Object> implements VideoDtoService {
    private final VideoDtoDao videoDtoDao;

    public VideoDtoServiceImpl(VideoDtoDao videoDtoDao) {
        this.videoDtoDao = videoDtoDao;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<VideoDto, Object> getPartVideo(Map<String, Object> parameters) {
        return (PageDto<VideoDto, Object>) super.getPageDto("getPartVideos", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<VideoDto, Object> getVideoOfAuthor(Map<String, Object> parameters) {
        return (PageDto<VideoDto, Object>) super.getPageDto("getVideoOfAuthor", parameters);
    }

    @Override
    public VideoDto getVideoOfName(String name) {
        return videoDtoDao.getVideoOfName(name).orElseThrow(() -> new IllegalArgumentException("Invalid parameters"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<VideoDto, Object> getPartVideoOfUser(Map<String, Object> parameters) {
        return (PageDto<VideoDto, Object>) super.getPageDto("getPartVideoOfUser", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<VideoDto, Object> getAuthorVideoOfUser(Map<String, Object> parameters) {
        return (PageDto<VideoDto, Object>) super.getPageDto("getAuthorVideoOfUser", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<VideoDto, Object> getAlbumVideoOfUser(Map<String, Object> parameters) {
        return (PageDto<VideoDto, Object>) super.getPageDto("getAlbumVideoOfUser", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<VideoDto, Object> getVideoFromAlbumOfUser(Map<String, Object> parameters) {
        return (PageDto<VideoDto, Object>) super.getPageDto("getVideoFromAlbumOfUser", parameters);
    }
}
