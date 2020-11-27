package com.javamentor.developer.social.platform.service.impl.dto.pagination;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
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
public class GroupPaginationServiceImpl<T, V> extends PaginationServiceImpl<T, V> {

    private PostDtoDao postDtoDao;

    @Autowired
    public void GroupPaginationService(PostDtoDao postDtoDao) {
        this.postDtoDao = postDtoDao;
    }

    public PageDto<T, V> getGroupPageDto(String methodName, Map<String, Object> parameters) {
        PageDto<GroupWallDto, ?> pageDto;
        try {
            pageDto = (PageDto<GroupWallDto, ?>) super.getPageDto(methodName, parameters);
        } catch (Exception e){
            throw new PaginationException("Invalid parameters or declared implementation. " +
                    "Please, make sure that parameters contains not null keys 'currentPage' and 'itemsOnPage'");
        }
        addMediasAndTags(pageDto);

        return (PageDto<T, V>) pageDto;
    }

    public void addMediasAndTags(PageDto <GroupWallDto, ?> pageDto){
        List<GroupWallDto> groupWallDtoList = pageDto.getItems();
        List<Long> postId = groupWallDtoList.stream().map(GroupWallDto::getId).collect(Collectors.toList());


        List<MediaPostDto> mediaDtoList = postDtoDao.getMediasByPostId(postId);
        List <TagDto> tagDtoList = postDtoDao.getTagsByPostId(postId);

        Map<Long, List<MediaPostDto>> mediaMap = new HashMap<>();
        Map<Long, List<TagDto>> tagMap = new HashMap<>();


        mediaDtoList.forEach(mediaPostDto -> {
            if(mediaPostDto != null) {
                if (!mediaMap.containsKey(mediaPostDto.getPostId())) {
                    mediaMap.put(mediaPostDto.getPostId(), new ArrayList<>());
                }
                mediaMap.get(mediaPostDto.getPostId()).add(mediaPostDto);
            }
        });
        groupWallDtoList.forEach(postDto -> {
            postDto.setMedia(mediaMap.get(postDto.getId()));
            if(postDto.getMedia() == null){
                postDto.setMedia(new ArrayList<>());
            }
        });

        tagDtoList.forEach(tagDto -> {
            if(tagDto != null) {
                if (!tagMap.containsKey(tagDto.getPostId())) {
                    tagMap.put(tagDto.getPostId(), new ArrayList<>());
                }
                tagMap.get(tagDto.getPostId()).add(tagDto);
            }
        });
        groupWallDtoList.forEach(postDto -> {
            postDto.setTags(tagMap.get(postDto.getId()));
            if(postDto.getTags() == null){
                postDto.setTags(new ArrayList<>());
            }
        });
        pageDto.setItems(groupWallDtoList);
    }
}
