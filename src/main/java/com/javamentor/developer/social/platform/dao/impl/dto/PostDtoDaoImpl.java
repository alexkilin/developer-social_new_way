package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDtoDaoImpl implements PostDtoDao {
    @Override
    public List<PostDto> getPosts() {
        return null;
    }
}
