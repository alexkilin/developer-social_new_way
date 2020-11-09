package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistCreateDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Service
public abstract class PlaylistConverter {

    protected UserService userService;
    protected AudioConverter audioConverter;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAudioConverter(AudioConverter audioConverter) {
        this.audioConverter = audioConverter;
    }

    @Mapping(target = "ownerUser", source = "ownerUserId", qualifiedByName = "userSetter")
    public abstract Playlist toEntity(PlaylistCreateDto playlistCreateDto);

    @Mapping(target = "ownerUserId", source = "ownerUser.userId")
    @Mapping(target = "content", source = "playlistContent", qualifiedByName = "contentSetter")
    public abstract PlaylistGetDto toPlaylistGetDto(Playlist playlist);

    @Named("userSetter")
    protected User userSetter(Long userId) {
        Optional<User> userOptional = userService.getById(userId);

        return userOptional.orElseThrow(() -> new EntityNotFoundException(String.format("User с id %s не существует", userId)));
    }

    @Named("contentSetter")
    protected List<AudioDto> contentSetter(Set<Audios> audiosSet) {
        if (audiosSet == null || audiosSet.size() == 0) {
            return null;
        }
        return audiosSet.stream()
               .map(audios -> audioConverter.toDto(audios))
               .collect(Collectors.toCollection(ArrayList<AudioDto>::new));
    }


}
