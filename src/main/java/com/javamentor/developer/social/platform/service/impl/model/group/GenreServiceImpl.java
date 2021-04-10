package com.javamentor.developer.social.platform.service.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.media.GenreDao;
import com.javamentor.developer.social.platform.models.entity.genre.Genre;
import com.javamentor.developer.social.platform.service.abstracts.model.media.GenreService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GenreServiceImpl  extends GenericServiceAbstract<Genre, Long> implements GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        super(genreDao);
        this.genreDao = genreDao;
    }

    @Override
    public Optional<Genre> getByTitle(String title) {
        return Optional.empty();
    }
}
