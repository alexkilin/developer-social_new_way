package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.VideoDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.VideoDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoDtoServiceImpl implements VideoDtoService {
    private final VideoDtoDao videoDtoDao;

    public VideoDtoServiceImpl(VideoDtoDao videoDtoDao) {
        this.videoDtoDao = videoDtoDao;
    }

    @Override
    @Transactional
    public List<VideoDto> getPartVideo(int currentPage, int itemsOnPage) {
        return this.videoDtoDao.getPartVideo(currentPage, itemsOnPage);
    }

    @Override
    @Transactional
    public List<VideoDto> getVideoOfAuthor(String author, int currentPage, int itemsOnPage) {
        return videoDtoDao.getVideoOfAuthor(author, currentPage, itemsOnPage);
    }

    @Override
    @Transactional
    public VideoDto getVideoOfName(String name) {
        return videoDtoDao.getVideoOfName(name).orElseThrow(() -> new IllegalArgumentException("Invalid parameters"));
    }

    @Override
    @Transactional
    public List<VideoDto> getVideoOfUser(Long userId) {
        return videoDtoDao.getVideoOfUser(userId);
    }

    @Override
    @Transactional
    public List<VideoDto> getPartVideoOfUser(Long userId, int currentPage, int itemsOnPage) {
        return videoDtoDao.getPartVideoOfUser(userId, currentPage, itemsOnPage);
    }

    @Override
    @Transactional
    public List<VideoDto> getAuthorVideoOfUser(Long userId, String author, int currentPage, int itemsOnPage) {
        return videoDtoDao.getAuthorVideoOfUser(userId, author, currentPage, itemsOnPage);
    }

    @Override
    @Transactional
    public List<VideoDto> getAlbumVideoOfUser(Long userId, String album, int currentPage, int itemsOnPage) {
        return videoDtoDao.getAlbumVideoOfUser(userId, album, currentPage, itemsOnPage);
    }

    @Override
    @Transactional
    public List<VideoDto> getVideoFromAlbumOfUser(Long albumId, int currentPage, int itemsOnPage) {
        return videoDtoDao.getVideoFromAlbumOfUser(albumId, currentPage, itemsOnPage);
    }
}
