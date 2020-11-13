package com.javamentor.developer.social.platform.webapp.controllers.v1;

import com.javamentor.developer.social.platform.models.dto.media.music.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistCreateDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumAudioDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.PlaylistDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.AudiosService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.PlaylistService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumConverter;
import com.javamentor.developer.social.platform.webapp.converters.AudioConverter;
import com.javamentor.developer.social.platform.webapp.converters.PlaylistConverter;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(value = "/api/audios", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "AudiosApi", description = "Операции с аудиозаписями(получение, сортировка, добавление)")
public class AudiosController {

    private final AudioConverter audioConverter;
    private final AlbumConverter albumConverter;
    private final AudioDtoService audioDtoService;
    private final AudiosService audiosService;
    private final UserService userService;
    private final AlbumService albumService;
    private final AlbumAudioDtoService albumAudioDtoService;
    private final AlbumAudioService albumAudioService;
    private final PlaylistDtoService playlistDtoService;
    private final PlaylistService playlistService;
    private final PlaylistConverter playlistConverter;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AudiosController(AudioConverter audioConverter, AlbumConverter albumConverter, AudioDtoService audioDtoService, AudiosService audiosService, UserService userService, AlbumService albumService, AlbumAudioDtoService albumAudioDtoService, AlbumAudioService albumAudioService, PlaylistDtoService playlistDtoService, PlaylistService playlistService, PlaylistConverter playlistConverter) {
        this.audioConverter = audioConverter;
        this.albumConverter = albumConverter;
        this.audioDtoService = audioDtoService;
        this.audiosService = audiosService;
        this.userService = userService;
        this.albumService = albumService;
        this.albumAudioDtoService = albumAudioDtoService;
        this.albumAudioService = albumAudioService;
        this.playlistDtoService = playlistDtoService;
        this.playlistService = playlistService;
        this.playlistConverter = playlistConverter;
    }

    @ApiOperation(value = "Получение всего аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио получено", responseContainer = "List",response = AudioDto.class)
    })
    @GetMapping(value = "/all")
    public ResponseEntity<List<AudioDto>> getAllAudios() {
        logger.info("Отправка всех аудио записей");
        return ResponseEntity.ok().body(audiosService.getAll().stream().map(audioConverter::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Получение некоторого количества аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Несколько аудио получено",responseContainer = "List",response = AudioDto.class)})
    @GetMapping(value = "/getPart", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<List<AudioDto>> getPartAudios(@ApiParam(value = "Текущая страница", example = "1")@RequestParam("currentPage") int currentPage,
                                                        @ApiParam(value = "Количество данных на страницу", example = "15")@RequestParam("itemsOnPage") int itemsOnPage) {
        logger.info(String.format("Аудио начиная c объекта номер %s, в количестве %s отправлено", (currentPage - 1) * itemsOnPage + 1, itemsOnPage));
        return ResponseEntity.ok().body(audioDtoService.getPartAudio(currentPage, itemsOnPage));
    }

    @ApiOperation(value = "Получение всего аудио одного автора")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио одного автора получено",response = AudioDto.class, responseContainer = "List")})
    @GetMapping(value = "/author/{author}")
    public ResponseEntity<List<AudioDto>> getAudioOfAuthor(@ApiParam(value = "Имя исполнителя", example = "Blur")@PathVariable @NotNull String author) {
        logger.info(String.format("Отправка всего аудио автора %s", author));
        return ResponseEntity.ok().body(audioDtoService.getAudioOfAuthor(author));
    }


    @ApiOperation(value = "Получение  аудио по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио по названию получено",response = AudioDto.class)})
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<List<AudioDto>> getAudioOfName(@ApiParam(value = "Название аудио", example = "Song2")@PathVariable @NotNull String name) {
        logger.info(String.format("Отправка всего аудио автора %s", name));
        return ResponseEntity.ok().body(audioDtoService.getAudioOfName(name));
    }

    @ApiOperation(value = "Получение всего аудио одного альбома")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио одного альбома получено",response = AudioDto.class,responseContainer = "List")})
    @GetMapping(value = "/album/{album}")
    public ResponseEntity<List<AudioDto>> getAudioOfAlbum(@ApiParam(value = "Название альбома", example = "The best")@PathVariable @NotNull String album) {
        logger.info(String.format("Отправка всего аудио альбома %s", album));
        return ResponseEntity.ok().body(audioDtoService.getAudioOfAlbum(album));
    }

    @ApiOperation(value = "Получение всего аудио из коллекции пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио из коллекции пользователя",response = AudioDto.class, responseContainer = "List")})
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<AudioDto>> getAudioOfUser(
            @ApiParam(value = "Id юзера", example = "1")@PathVariable("userId") @NonNull Long userId) {
        logger.info(String.format("Отправка всего аудио пользователя %s", userId));
        return ResponseEntity.ok().body(audioDtoService.getAudioOfUser(userId));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по частям")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио из коллекции пользователя по частям",responseContainer = "List",response = AudioDto.class)})
    @GetMapping(value = "/PartAudioOfUser/{userId}", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<List<AudioDto>> getPartAudioOfUser(
            @ApiParam(value = "Текущая страница", example = "1")@RequestParam("currentPage") int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "15")@RequestParam("itemsOnPage") int itemsOnPage,
            @ApiParam(value = "Id юзера", example = "1")@PathVariable("userId") @NonNull Long userId) {
        logger.info(String.format("Аудио пользователя %s начиная c объекта номер %s, в количестве %s отправлено ", userId, (currentPage - 1) * itemsOnPage + 1, itemsOnPage));
        return ResponseEntity.ok().body(audioDtoService.getPartAudioOfUser(userId, currentPage, itemsOnPage));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по автору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио из коллекции пользователя по автору", response = AudioDto.class, responseContainer = "List")})
    @GetMapping(value = "/AuthorAudioOfUser")
    public ResponseEntity<List<AudioDto>> getAuthorAudioOfUser(@ApiParam(value = "Имя исполнителя", example = "Blur")@RequestParam("author") String author) {
        logger.info(String.format("Отправка избранного аудио пользователя c id %s автора %s", 60L, author));
        return ResponseEntity.ok().body(audioDtoService.getAuthorAudioOfUser(60L, author));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по альбому")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио из коллекции пользователя по альбому",response = AudioDto.class,responseContainer = "List")})
    @GetMapping(value = "/AlbumAudioOfUser", params = {"album"})
    public ResponseEntity<List<AudioDto>> getAlbumAudioOfUser(@ApiParam(value = "Название альбома",example = "My Album")@RequestParam("album") String album) {
        logger.info(String.format("Отправка избранного аудио пользователя c id %s альбома %s", 60L, album));
        return ResponseEntity.ok().body(audioDtoService.getAlbumAudioOfUser(60L, album));
    }

    @ApiOperation(value = "Добавление аудио в избранное пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио успешно добавлено")})
    @PostMapping(value = "/addToUser", params = {"audioId"})
    public ResponseEntity<?> addAudioInCollectionsOfUser(@ApiParam(value = "Id музыке",example = "153")@RequestParam("audioId") Long audioId) {
        Optional<User> userOptional = userService.getById(60L);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Пользователя с id %s не найдено", 60L));
        }
        User user = userOptional.get();
        if(audiosService.addAudioInCollectionsOfUser(user, audioId)){
            userService.update(user);
            logger.info(String.format("Успешное добавление аудио с id %s в избранное пользователю с id %s", audioId, 60L));
            return ResponseEntity.ok().body("Успешно");
       } else {
            return ResponseEntity.ok().body(String.format("Неудачное добавление аудио с id %s в избранное пользователю с id %s", audioId, 60L));
        }
    }

    @ApiOperation(value = "Добавление аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио успешно добавлено", response = AudioDto.class)})
   // @Validated(OnCreate.class)
    @PostMapping(value = "/add")
    public ResponseEntity<?> addAudio(@ApiParam(value = "Объект добавляемого аудио")@RequestBody @Valid @NonNull AudioDto audioDto) {
        Optional<User> userOptional = userService.getById(60L);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Пользователя с id %s не найдено", 60L));
        }
        User user = userOptional.get();
        Audios audios = audioConverter.toAudio(audioDto, MediaType.AUDIO, user);
        audiosService.create(audios);
        logger.info(String.format("Добавление аудио с id %s в бд", audioDto.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(audioConverter.toDto(audios));
    }

    @ApiOperation(value = "Получение всех альбомов пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Альбомы успешно получены", response = AlbumDto.class,responseContainer = "List"),
            @ApiResponse(code = 404, message = "Альбомы не найдены")
    })
    @GetMapping(value = "/getAllAlbumsFromUser")
    public ResponseEntity<List<AlbumAudioDto>> getAllAlbums() {
        logger.info(String.format("Получение всех альбомов пользователя с id %s", userService.getPrincipal().getUserId()));
        return ResponseEntity.ok().body(albumAudioDtoService.getAllByUserId(userService.getPrincipal().getUserId()));
    }


    @ApiOperation(value = "Добавить аудио в альбом")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио в альбом успешно добавлено", response = String.class)})
    @PostMapping(value = "/addInAlbums", params = {"albumId", "audioId"})
    public ResponseEntity<?> addInAlbums(@ApiParam(value = "Id альбома",example = "242")@RequestParam @Valid @NotNull Long albumId,
                                         @ApiParam(value = "Id альбома",example = "242")@RequestParam @NotNull Long audioId) {
        logger.info(String.format("Аудио с id  %s добавлено в альбом с id %s", audioId, albumId));
        Optional<AlbumAudios> albumAudiosOptional = albumAudioService.getById(albumId);
        if (!albumAudiosOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("AlbumAudios id %s not found", albumId));
        }
        Optional<Audios> audiosOptional = audiosService.getById(audioId);
        if (!audiosOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Аудио с id %s не найдено", audioId));
        }
        AlbumAudios albumAudios = albumAudiosOptional.get();
        Set<Audios> audiosSet = albumAudios.getAudios();
        audiosSet.add(audiosOptional.get());
        albumAudios.setAudios(audiosSet);
        albumAudioService.create(albumAudios);
        return ResponseEntity.ok().body(String.format("Аудио с id  %s добавлено в альбом с id %s", audioId, albumId));
    }

    @ApiOperation(value = "Создание альбома пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио в альбом успешно добавлено", response = AlbumDto.class),
            @ApiResponse(code = 400, message = "Неверные параметры", response = String.class)})
    @Validated(OnCreate.class)
    @PostMapping(value = "/createAlbum")
    public ResponseEntity<?> createAlbum(@ApiParam(value = "объект создаваемого альбома")@RequestBody @Valid @NotNull AlbumDto albumDto) {
        if(albumService.existsByNameAndMediaType(albumDto.getName(), MediaType.AUDIO)) {
            return ResponseEntity.badRequest()
                    .body(String.format("Audio album with name '%s' already exists", albumDto.getName()));
        }
        Optional<User> userOptional = userService.getById(60L);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Пользователь с %d id не найден", 60L));
        }
        AlbumAudios albumAudios = albumAudioService.createAlbumAudiosWithOwner(
                albumConverter.toAlbumAudios(albumDto, userOptional.get()));
        logger.info(String.format("Альбом с именем  %s создан", albumDto.getName()));
        return ResponseEntity.ok().body(albumConverter.toAlbumDto(albumAudios));
    }

    @ApiOperation(value = "Получение всех аудио из альбома пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из альбома пользователя успешно получено", response = AudioDto.class,responseContainer = "List")})
    @GetMapping(value = "/getFromAlbumOfUser", params = {"albumId"})
    public ResponseEntity<?> getFromAlbumOfUser(@ApiParam(value = "Id альбома", example = "7")@RequestParam @NotNull Long albumId) {
        logger.info(String.format("Все аудио из альбома с id:%s отправлено", albumId));
        return ResponseEntity.ok().body(audioDtoService.getAudioFromAlbumOfUser(albumId));
    }

    @ApiOperation(value = "Создание нового плейлиста для текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Плейлист успешно создан", response = PlaylistGetDto.class)})
    @Validated(OnCreate.class)
    @PostMapping(value = "/playlists")
    public ResponseEntity<?> createPlaylist(@ApiParam(value = "Объект нового плейлиста") @RequestBody @NotNull @Valid PlaylistCreateDto playlistCreateDto) {
        playlistCreateDto.setOwnerUserId(60L);
        PlaylistGetDto playlistGetDto = playlistDtoService.create(playlistCreateDto);
        logger.info(String.format("Плейлист id %s для пользователя %s создан", playlistGetDto.getId(), 60L));
        return ResponseEntity.ok().body(playlistGetDto);
    }

    @ApiOperation(value = "Удаление плейлиста по Id для текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Плейлист удален"),
            @ApiResponse(code = 404, message = "Плейлист не найден")})
    @DeleteMapping(value = "/playlists/{playlistId}")
    public ResponseEntity<?> deletePlaylist(@ApiParam(value = "Id плейлиста", example = "2") @PathVariable @NotNull Long playlistId) {
        if (!playlistService.userHasPlaylist(60L, playlistId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No playlist with id %s for current user", playlistId));
        }
        playlistService.deleteById(playlistId);
        logger.info(String.format("Плейлист id %s удален", playlistId));
        return ResponseEntity.ok().body(String.format("Playlist with id %s deleted", playlistId));
    }

    @ApiOperation(value = "Получение списка плейлистов текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Плейлисты получены", response = PlaylistGetDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "У текущего пользователя нет плейлистов")})
    @GetMapping(value = "/playlists")
    public ResponseEntity<?> getPlaylistsOfUser() {
        List<PlaylistGetDto> playlistGetDtoList = playlistDtoService.getAllByUserId(60L);
        if (playlistGetDtoList.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No playlists exist for current user");
        }
        logger.info(String.format("Плейлисты пользователя %s отправлены", 60L));
        return ResponseEntity.ok().body(playlistGetDtoList);
    }

    @ApiOperation(value = "Получение плейлиста по Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Плейлист получен", response = PlaylistGetDto.class),
            @ApiResponse(code = 404, message = "Плейлист не найден")})
    @GetMapping(value = "/playlists/{playlistId}")
    public ResponseEntity<?> getPlaylistById(@ApiParam(value = "Id плейлиста", example = "2")@PathVariable @NotNull Long playlistId) {
        Optional<PlaylistGetDto> optionalPlaylistGetDto = playlistDtoService.getById(playlistId);
        if (!optionalPlaylistGetDto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No playlist with id %s", playlistId));
        }
        logger.info(String.format("Плейлист %s отправлен", playlistId));
        return ResponseEntity.ok().body(optionalPlaylistGetDto.get());
    }

    @ApiOperation(value = "Добавление аудио в плейлист для текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио добавлено", response = PlaylistGetDto.class),
            @ApiResponse(code = 400, message = "Плейлист уже содержит это аудио"),
            @ApiResponse(code = 404, message = "Плейлист или аудио не найдены")})
    @PutMapping(value = "/playlists/{playlistId}/audio")
    public ResponseEntity<?> addAudioToPlaylist(@ApiParam(value = "Id плейлиста", example = "2")@PathVariable @NotNull Long playlistId,
                                                @ApiParam(value = "Id аудио", example = "10")@RequestParam("audioId") @NotNull Long audioId) {
        Optional<Playlist> playlistOptional = playlistService.getOptionalById(playlistId);
        if (!playlistOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No playlist with id %s for current user", playlistId));
        }
        Optional<Audios> audiosOptional = audiosService.getOptionalById(audioId);
        if (!audiosOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No audio with id %s found", audioId));
        }
        Playlist playlist = playlistOptional.get();
        if (!playlist.addAudio(audiosOptional.get())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("This playlist already contains audio with id %s", audioId));
        }
        playlistService.update(playlist);
        logger.info(String.format("Аудио %s добавлено в плейлист %s", audioId, playlistId));
        return ResponseEntity.ok().body(playlistConverter.toPlaylistGetDto(playlist));
    }

    @ApiOperation(value = "Удаление аудио из плейлиста для текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио удалено", response = PlaylistGetDto.class),
            @ApiResponse(code = 400, message = "Плейлист не содержит это аудио"),
            @ApiResponse(code = 404, message = "Плейлист или аудио не найдены")})
    @DeleteMapping(value = "/playlists/{playlistId}/audio/{audioId}")
    public ResponseEntity<?> removeAudioFromPlaylist(@ApiParam(value = "Id плейлиста", example = "2")@PathVariable @NotNull Long playlistId,
                                                     @ApiParam(value = "Id аудио", example = "10")@PathVariable("audioId") @NotNull Long audioId) {
        Optional<Playlist> playlistOptional = playlistService.getOptionalById(playlistId);
        if (!playlistOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No playlist with id %s for current user", playlistId));
        }
        Optional<Audios> audiosOptional = audiosService.getOptionalById(audioId);
        if (!audiosOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No audio with id %s found", audioId));
        }
        Playlist playlist = playlistOptional.get();
        if (!playlist.removeAudio(audiosOptional.get())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("This playlist has no audio with id %s", audioId));
        }
        playlistService.update(playlist);
        logger.info(String.format("Аудио %s удалено из плейлиста %s", audioId, playlistId));
        return ResponseEntity.ok().body(playlistConverter.toPlaylistGetDto(playlist));
    }

    @ApiOperation(value = "Получение содержимого плейлиста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио отправлено", response = AudioDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Плейлист найден")})
    @GetMapping(value = "/playlists/{playlistId}/audio")
    public ResponseEntity<?> getAudioFromPlaylist(@ApiParam(value = "Id плейлиста", example = "2")@PathVariable @NotNull Long playlistId,
                                                  @ApiParam(value = "Отступ", example = "0")@RequestParam("offset") @NotNull int offset,
                                                  @ApiParam(value = "Количество данных на страницу", example = "10")@RequestParam("limit") @NotNull int limit) {
        if (!playlistService.existById(playlistId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No playlist with id %s", playlistId));
        }
        logger.info(String.format("Аудио из плейлиста %s отправлено", playlistId));
        return ResponseEntity.ok().body(audioDtoService.getAudioFromPlaylist(playlistId, offset, limit));
    }

}
