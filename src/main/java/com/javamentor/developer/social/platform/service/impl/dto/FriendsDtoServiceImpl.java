package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserFriendsDtoDao;
import com.javamentor.developer.social.platform.models.dto.FriendDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.FriendsDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsDtoServiceImpl implements FriendsDtoService {

    private final UserFriendsDtoDao dao;

    @Autowired
    public FriendsDtoServiceImpl(UserFriendsDtoDao dao) {
        this.dao = dao;
    }

    @Override
    public List<FriendDto> getUserFriendsDtoById(Long id) {
        return dao.getUserFriendsDtoById(id);
    }
}
