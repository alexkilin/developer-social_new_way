package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    public final UserDto convertToDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getEducation(),
                user.getAboutMe(),
                user.getAvatar(),
                user.getEmail(),
                user.getPassword(),
                user.getPersistDate(),
                user.getLastRedactionDate(),
                user.getCity(),
                user.getLinkSite(),
                user.getRole(),
                user.getStatus(),
                user.getActive(),
                user.getLanguages(),
                user.getMessages(),
                user.getPosts());
    }

    public final User convertToEntity(UserDto userDto) {
        return null;
    }
}
