package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GenreDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.GenreDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GenreDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class GenreDtoServiceImpl  extends PaginationServiceImpl<GenreDto,Object> implements GenreDtoService {

    private final GenreDtoDao genreDtoDao;

    @Autowired
    public GenreDtoServiceImpl(GenreDtoDao genreDtoDao) {
        this.genreDtoDao = genreDtoDao;
    }

    @Override
    public Optional<GenreDto> getGenreByTitle(String title) {
        return genreDtoDao.getGenreByTitle(title);
    }

    @Override
    public PageDto<GenreDto, Object> getAllGenres(Map<String, Object> parameters) {
        return super.getPageDto("getAllCategories" , parameters);
    }
}
