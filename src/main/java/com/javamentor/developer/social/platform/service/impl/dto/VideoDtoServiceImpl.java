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
    public PageDto<VideoDto, Object> getPartVideo(Map<String, Object> parameters) {
        return super.getPageDto("getPartVideos", parameters);
    }

    @Override
    public PageDto<VideoDto, ?> getVideoSortedByLikes(Map<String, Object> parameters) {
        return super.getPageDto("getVideoSortedByLikes", parameters);
    }

    @Override
    public PageDto<VideoDto, Object> getVideoOfAuthor(Map<String, Object> parameters) {
        return super.getPageDto("getVideoOfAuthor", parameters);
    }

    @Override
    public PageDto<VideoDto, Object> getVideoOfNamePart(Map<String, Object> parameters) {
        return super.getPageDto("getVideoOfNamePart", parameters);
    }

    @Override
    public PageDto<VideoDto, Object> getPartVideoOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getPartVideoOfUser", parameters);
    }

    @Override
    public PageDto<VideoDto, Object> getAuthorVideoOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getAuthorVideoOfUser", parameters);
    }

    @Override
    public PageDto<VideoDto, Object> getAlbumVideoOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getAlbumVideoOfUser", parameters);
    }

    @Override
    public PageDto<VideoDto, Object> getVideoFromAlbumOfUser(Map<String, Object> parameters) {
        return super.getPageDto("getVideoFromAlbumOfUser", parameters);
    }
}
