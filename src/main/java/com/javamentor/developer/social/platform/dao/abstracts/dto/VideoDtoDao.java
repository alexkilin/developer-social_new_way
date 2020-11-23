package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;

import java.util.List;
import java.util.Optional;

public interface VideoDtoDao {

    List<VideoDto> getVideoOfAuthor(String author, int currentPage, int itemsOnPage);

    Optional<VideoDto> getVideoOfName(String name);

    List<VideoDto> getVideoOfUser(Long userId);

    List<VideoDto> getPartVideoOfUser(Long userId, int currentPage, int itemsOnPage);

    List<VideoDto> getAuthorVideoOfUser(Long userId, String author, int currentPage, int itemsOnPage);

    List<VideoDto> getAlbumVideoOfUser(Long userId, String album, int currentPage, int itemsOnPage);

    boolean addVideoInCollectionsOfUser(Long userId, Long videoId);

    List<VideoDto> getVideoFromAlbumOfUser(Long albumId, int currentPage, int itemsOnPage);

    List<VideoDto> getPartVideo(int currentPage, int itemsOnPage);
}
