package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.VideoDto;

import java.util.List;
import java.util.Optional;

public interface VideoDtoDao {

    List<VideoDto> getVideoOfAuthor(String author);

    Optional<VideoDto> getVideoOfName(String name);

    List<VideoDto> getVideoOfAlbum(String album);

    List<VideoDto> getVideoOfUser(Long userId);

    List<VideoDto> getPartVideoOfUser(Long userId, int currentPage, int itemsOnPage);

    List<VideoDto> getAuthorVideoOfUser(Long userId, String author);

    List<VideoDto> getAlbumVideoOfUser(Long userId, String album);

    boolean addVideoInCollectionsOfUser(Long userId, Long videoId);

    List<VideoDto> getVideoFromAlbumOfUser(Long albumId);
}
