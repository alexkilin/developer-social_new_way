package com.javamentor.developer.social.platform.service.impl.dto.pagination;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostPaginationService<T, V> extends PaginationServiceImpl<Object, Object> {

    private PostDtoDao postDtoDao;

    @Autowired
    public void setPostDtoDao(PostDtoDao postDtoDao) {
        this.postDtoDao = postDtoDao;
    }

    @SuppressWarnings("unchecked")
    public PageDto<? extends T, ? extends V> getPostPageDto(String methodName, Map<String, Object> parameters) {
        PageDto<?, ?> pageDto;
            pageDto = super.getPageDto(methodName, parameters);
        addMediasAndTags((PageDto<PostDto, ?>) pageDto);

        return (PageDto<T, V>) pageDto;
    }

    public void addMediasAndTags(PageDto <PostDto, ?> pageDto){
        List<PostDto> postDtoList = pageDto.getItems();
        List<Long> postId = postDtoList.stream().map(PostDto::getId).collect(Collectors.toList());

        Map<Long, List<MediaPostDto>> mediaMap = getAllMedia(postId);
        Map<Long, List<TagDto>> tagMap = getAllTags(postId);


        postDtoList.forEach(postDto -> {
            postDto.setTags(tagMap.get(postDto.getId()));
            if(postDto.getTags() == null){
                postDto.setTags(new ArrayList<>());
            }

            postDto.setMedia(mediaMap.get(postDto.getId()));
            if(postDto.getMedia() == null){
                postDto.setMedia(new ArrayList<>());
            }
        });
        pageDto.setItems(postDtoList);
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
