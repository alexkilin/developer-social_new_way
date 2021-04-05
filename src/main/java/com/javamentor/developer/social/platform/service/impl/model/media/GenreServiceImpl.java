package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.GenreDao;
import com.javamentor.developer.social.platform.models.entity.genre.Genre;
import com.javamentor.developer.social.platform.service.abstracts.model.media.GenreService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreServiceImpl extends GenericServiceAbstract<Genre, Long> implements GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao dao) {
        super(dao);
        this.genreDao = dao;
    }

    @Override
    public Optional<Genre> getByTitle(String title) {
        return genreDao.getByTitle(title);
    }
}
