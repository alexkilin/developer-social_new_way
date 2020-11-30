package com.javamentor.developer.social.platform.service.impl.dto.pagination;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GroupPaginationService<T, V> extends PaginationServiceImpl<T, V> {

    private PostDtoDao postDtoDao;

    @Autowired
    public void setPostDtoDao(PostDtoDao postDtoDao) {
        this.postDtoDao = postDtoDao;
    }

    public PageDto<? extends T, ? extends V> getGroupPageDto(String methodName, Map<String, Object> parameters) {
        PageDto<GroupWallDto, Object> pageDto;
        try {
            pageDto = (PageDto<GroupWallDto, Object>) super.getPageDto(methodName, parameters);
        } catch (Exception e){
            throw new PaginationException("Invalid parameters or declared implementation. " +
                    "Please, make sure that parameters contains not null keys 'currentPage' and 'itemsOnPage'");
        }
        addMediasAndTags(pageDto);

        return (PageDto<? extends T, ? extends V>) pageDto;
    }

    public void addMediasAndTags(PageDto <GroupWallDto, Object> pageDto){
        List<GroupWallDto> groupWallDtoList = pageDto.getItems();
        List<Long> postId = groupWallDtoList.stream().map(GroupWallDto::getId).collect(Collectors.toList());


        Map<Long, List<MediaPostDto>> mediaMap = getAllMedia(postId);
        Map<Long, List<TagDto>> tagMap = getAllTags(postId);


        groupWallDtoList.forEach(wallDto -> {
            wallDto.setTags(tagMap.get(wallDto.getId()));
            if(wallDto.getTags() == null){
                wallDto.setTags(new ArrayList<>());
            }

            wallDto.setMedia(mediaMap.get(wallDto.getId()));
            if(wallDto.getMedia() == null){
                wallDto.setMedia(new ArrayList<>());
            }
        });
        pageDto.setItems(groupWallDtoList);
    }

    public Map<Long, List<TagDto>> getAllTags(List<Long> postId){
        List <TagDto> tagDtoList = postDtoDao.getTagsByPostId(postId);

        Map<Long, List<TagDto>> tagMap = new HashMap<>();

        tagDtoList.forEach(tagDto -> {
            if(tagDto != null) {
                if (!tagMap.containsKey(tagDto.getPostId())) {
                    tagMap.put(tagDto.getPostId(), new ArrayList<>());
                }
                tagMap.get(tagDto.getPostId()).add(tagDto);
            }
        });

        return tagMap;
    }

    public Map<Long, List<MediaPostDto>> getAllMedia(List<Long> postId){
        List<MediaPostDto> mediaDtoList = postDtoDao.getMediasByPostId(postId);

        Map<Long, List<MediaPostDto>> mediaMap = new HashMap<>();

        mediaDtoList.forEach(mediaPostDto -> {
            if(mediaPostDto != null) {
                if (!mediaMap.containsKey(mediaPostDto.getPostId())) {
                    mediaMap.put(mediaPostDto.getPostId(), new ArrayList<>());
                }
                mediaMap.get(mediaPostDto.getPostId()).add(mediaPostDto);
            }
        });

        return mediaMap;
    }
}
