package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.VideoDtoDao;
import com.javamentor.developer.social.platform.models.dto.VideoDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.VideoDtoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoDtoServiceImpl implements VideoDtoService {

    private final VideoDtoDao videoDtoDao;

    public VideoDtoServiceImpl(VideoDtoDao videoDtoDao){
        this.videoDtoDao = videoDtoDao;
    }

    @Override
    public List<VideoDto> getVideoOfAuthor(String author) {
        return null;
    }

    @Override
    public VideoDto getVideoOfName(String name) {
        return videoDtoDao.getVideoOfName(name).orElseThrow(() -> new IllegalArgumentException("Invalid parameters"));
    }

    @Override
    public List<VideoDto> getVideoOfAlbum(String album) {
        return null;
    }

    @Override
    public List<VideoDto> getVideoOfUser(Long userId) {
        return null;
    }

    @Override
    public List<VideoDto> getPartVideoOfUser(Long userId, int currentPage, int itemsOnPage) {
        return null;
    }

    @Override
    public List<VideoDto> getAuthorVideoOfUser(Long userId, String author) {
        return null;
    }

    @Override
    public List<VideoDto> getAlbumVideoOfUser(Long userId, String album) {
        return null;
    }

    @Override
    public boolean addVideoInCollectionsOfUser(Long userId, Long videoId) {
        return false;
    }

    @Override
    public List<VideoDto> getVideoFromAlbumOfUser(Long albumId) {
        return null;
    }
}
