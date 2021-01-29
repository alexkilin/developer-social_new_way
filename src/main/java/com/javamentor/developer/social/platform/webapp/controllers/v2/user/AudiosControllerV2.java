package com.javamentor.developer.social.platform.webapp.controllers.v2.user;

import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.music.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistCreateDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumAudioDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.PlaylistDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.AudiosService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.PlaylistService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumAudioConverter;
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
import java.util.*;

@Validated
@RestController
@RequestMapping(value = "/api/v2/audio", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "AudiosApi-v2", description = "Операции над аудиозаписями")
public class AudiosControllerV2 {

    private final AudioConverter audioConverter;
    private final AlbumAudioConverter albumAudioConverter;
    private final AudioDtoService audioDtoService;
    private final AudiosService audiosService;
    private final UserService userService;
    private final AlbumService albumService;
    private final AlbumAudioDtoService albumAudioDtoService;
    private final AlbumAudioService albumAudioService;
    private final PlaylistDtoService playlistDtoService;
    private final PlaylistService playlistService;
    private final PlaylistConverter playlistConverter;
    private final SecurityHelper securityHelper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AudiosControllerV2(AudioConverter audioConverter, AlbumAudioConverter albumAudioConverter, AudioDtoService audioDtoService,
                              AudiosService audiosService, UserService userService, AlbumService albumService,
                              AlbumAudioDtoService albumAudioDtoService, AlbumAudioService albumAudioService,
                              PlaylistDtoService playlistDtoService, PlaylistService playlistService,
                              PlaylistConverter playlistConverter, SecurityHelper securityHelper) {
        this.audioConverter = audioConverter;
        this.albumAudioConverter = albumAudioConverter;
        this.audioDtoService = audioDtoService;
        this.audiosService = audiosService;
        this.userService = userService;
        this.albumService = albumService;
        this.albumAudioDtoService = albumAudioDtoService;
        this.albumAudioService = albumAudioService;
        this.playlistDtoService = playlistDtoService;
        this.playlistService = playlistService;
        this.playlistConverter = playlistConverter;
        this.securityHelper = securityHelper;
    }

    @ApiOperation(value = "Получение всего аудио постранично")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио получено", responseContainer = "List", response = PageDto.class)})
    @GetMapping(params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<AudioDto, ?>> getPartAudios(@ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                              @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {

        logger.info(String.format("Аудио начиная c объекта номер %s, в количестве %s отправлено", (currentPage - 1) * itemsOnPage + 1, itemsOnPage));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(audioDtoService.getPartAudio(parameters));
    }

    @ApiOperation(value = "Получение аудио по автору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио по автору получено", response = PageDto.class, responseContainer = "List")})
    @GetMapping(value = "/author/{author}", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<AudioDto, ?>> getAudioOfAuthor(@ApiParam(value = "Имя исполнителя", example = "Blur") @PathVariable @NotNull String author,
                                                                 @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                                 @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {

        logger.info(String.format("Отправка всего аудио автора %s", author));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", author);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(audioDtoService.getAudioOfAuthor(parameters));
    }


    @ApiOperation(value = "Получение аудио по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио по названию получено", response = PageDto.class)})
    @GetMapping(value = "/name/{name}", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<AudioDto, ?>> getAudioOfName(@ApiParam(value = "Название аудио", example = "Song2") @PathVariable @NotNull String name,
                                                               @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                               @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {

        logger.info(String.format("Отправка аудио %s", name));
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(audioDtoService.getAudioOfName(parameters));
    }

    @ApiOperation(value = "Получение аудио по альбому")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио по альбому получено", response = PageDto.class, responseContainer = "List")})
    @GetMapping(value = "/album/{album}", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<AudioDto, ?>> getAudioOfAlbum(@ApiParam(value = "Название альбома", example = "The best") @PathVariable @NotNull String album,
                                                                @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                                @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {

        logger.info(String.format("Отправка всего аудио альбома %s", album));
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("album", album);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(audioDtoService.getAudioOfAlbum(parameters));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя постранично")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио из коллекции пользователя постранично", responseContainer = "List", response = PageDto.class)})
    @GetMapping(value = "/user/{userId}", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<AudioDto, ?>> getPartAudioOfUser(
            @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage,
            @ApiParam(value = "Id юзера", example = "60") @PathVariable("userId") @NonNull Long userId) {

        logger.info(String.format("Аудио пользователя %s начиная c объекта номер %s, в количестве %s отправлено ", userId, (currentPage - 1) * itemsOnPage + 1, itemsOnPage));
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(audioDtoService.getPartAudioOfUser(parameters));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по автору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио из коллекции пользователя по автору", response = PageDto.class, responseContainer = "List")})
    @GetMapping(value = "/user/{userId}/author", params = {"author", "currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<AudioDto, ?>> getAuthorAudioOfUser(@ApiParam(value = "Имя исполнителя", example = "Blur") @RequestParam("author") String author,
                                                                     @ApiParam(value = "Id юзера", example = "60") @PathVariable("userId") @NonNull Long userId,
                                                                     @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                                     @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        logger.info(String.format("Отправка избранного аудио пользователя c id %s автора %s", userId, author));
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("author", author);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(audioDtoService.getAuthorAudioOfUser(parameters));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по альбому")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио из коллекции пользователя по альбому", response = PageDto.class, responseContainer = "List")})
    @GetMapping(value = "/user/{userId}/album", params = {"album", "currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<AudioDto, ?>> getAlbumAudioOfUser(@ApiParam(value = "Название альбома", example = "My Album") @RequestParam("album") String album,
                                                                    @ApiParam(value = "Id юзера", example = "60") @PathVariable("userId") @NonNull Long userId,
                                                                    @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                                    @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        logger.info(String.format("Отправка избранного аудио пользователя c id %s альбома %s", userId, album));
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("album", album);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(audioDtoService.getAlbumAudioOfUser(parameters));
    }

    @ApiOperation(value = "Добавление аудио в коллекцию пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио успешно добавлено"),
            @ApiResponse(code = 500, message = "Текущий пользователь не найден в базе данных")})
    @PutMapping(value = "/user/audio", params = {"audioId"})
    public ResponseEntity<?> addAudioInCollectionsOfUser(@ApiParam(value = "Id аудио", example = "71") @RequestParam("audioId") Long audioId) {
         return userService.getByIdWithAudios(securityHelper.getPrincipal().getUserId())
                 .map(user -> {
                     audiosService.addAudioInCollectionsOfUser(user, audioId);
                     logger.info(String.format("Аудио id %s добавлено в коллекцию пользователя id %s", audioId, user.getUserId()));
                     return ResponseEntity.ok().body(String.format("Audio id %s added to collection of user id %s", audioId, user.getUserId()));
                }).orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Current user is not found in the database"));
    }

    @ApiOperation(value = "Добавление (создание) аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио успешно создано", response = AudioDto.class)})
    @Validated(OnCreate.class)
    @PostMapping(value = "/user/{userId}/audio")
    public ResponseEntity<?> addAudio(@ApiParam(value = "Объект добавляемого аудио") @RequestBody @Valid @NonNull AudioDto audioDto,
                                      @ApiParam(value = "Id юзера", example = "60") @PathVariable("userId") @NonNull Long userId) {
        Optional<User> user = userService.getById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User id %s not found", userId));
        }
        Audios audios = audioConverter.toAudio(audioDto, MediaType.AUDIO, user.get());
        audiosService.create(audios);
        logger.info(String.format("Добавление аудио с id %s в бд", audioDto.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(audioConverter.toDto(audios));
    }

    @ApiOperation(value = "Получение всех альбомов пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Альбомы успешно получены", response = PageDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Альбомы не найдены")
    })
    @GetMapping(value = "/user/{userId}/album", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<AlbumAudioDto, ?>> getAllAlbums(@ApiParam(value = "Id юзера", example = "60") @PathVariable("userId") @NonNull Long userId,
                                                                  @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                                  @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        logger.info(String.format("Получение всех альбомов пользователя с id %s", userId));
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);

        return ResponseEntity.ok().body(albumAudioDtoService.getAllByUserId(parameters));
    }

    @ApiOperation(value = "Добавить существующее аудио в альбом")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио в альбом успешно добавлено", response = String.class)})
    @PutMapping(value = "/albums/{albumId}/audio", params = {"audioId"})
    public ResponseEntity<?> addInAlbums(@ApiParam(value = "Id альбома", example = "5") @PathVariable @NotNull Long albumId,
                                         @ApiParam(value = "Id аудио", example = "11") @RequestParam @NotNull Long audioId) {
        logger.info(String.format("Аудио с id  %s добавлено в альбом с id %s", audioId, albumId));
        Optional<AlbumAudios> albumAudiosOptional = albumAudioService.getByIdWithAudios(albumId);
        if (!albumAudiosOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Album id %s not found", albumId));
        }
        Optional<Audios> audiosOptional = audiosService.getById(audioId);
        if (!audiosOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Audio id %s not found", audioId));
        }
        AlbumAudios albumAudios = albumAudiosOptional.get();
        Set<Audios> audiosSet = albumAudios.getAudios();
        if (!audiosSet.add(audiosOptional.get())) {
            return ResponseEntity.badRequest().body(String.format("This album already contains audio with id %s", audioId));
        }
        audiosSet.add(audiosOptional.get());
        albumAudios.setAudios(audiosSet);
        albumAudioService.update(albumAudios);
        return ResponseEntity.ok().body(String.format("Audio id %s added to album id %s", audioId, albumId));
    }

    @ApiOperation(value = "Создание альбома пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Альбом успешно создан", response = AlbumDto.class),
            @ApiResponse(code = 400, message = "Неверные параметры", response = String.class)})
    @Validated(OnCreate.class)
    @PostMapping(value = "/user/{userId}/album")
    public ResponseEntity<?> createAlbum(@ApiParam(value = "объект создаваемого альбома") @RequestBody @Valid @NotNull AlbumDto albumDto,
                                         @ApiParam(value = "Id юзера", example = "60") @PathVariable("userId") @NonNull Long userId) {
        if (albumService.existsByNameAndMediaType(albumDto.getName(), MediaType.AUDIO)) {
            return ResponseEntity.badRequest()
                    .body(String.format("Audio album with name '%s' already exists", albumDto.getName()));
        }
        AlbumAudios albumAudios = albumAudioService.createAlbumAudiosWithOwner(
                albumAudioConverter.toAlbumAudios(albumDto, userService.getByIdWithAudios(userId).get()));
        logger.info(String.format("Альбом с именем  %s создан", albumDto.getName()));
        return ResponseEntity.ok().body(albumAudioConverter.toAlbumAudioDto(albumAudios));
    }

    @ApiOperation(value = "Получение всех аудио из альбома")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио из альбома получено", response = PageDto.class, responseContainer = "List")})
    @GetMapping(value = "/albums/{albumId}/audio", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<AudioDto, ?>> getFromAlbumOfUser(@ApiParam(value = "Id альбома", example = "7") @PathVariable @NotNull Long albumId,
                                                                   @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                                   @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        logger.info(String.format("Все аудио из альбома с id:%s отправлено", albumId));
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("albumId", albumId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(audioDtoService.getAudioFromAlbumOfUser(parameters));
    }

    @ApiOperation(value = "Создание нового плейлиста для пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Плейлист успешно создан", response = PlaylistGetDto.class),
            @ApiResponse(code = 200, message = "Плейлист с таким названием уже существует", response = PlaylistGetDto.class),
            @ApiResponse(code = 400, message = "Такого пользователя не существует")})
    @Validated(OnCreate.class)
    @PostMapping(value = "/user/{userId}/playlists")
    public ResponseEntity<?> createPlaylist(@ApiParam(value = "Объект нового плейлиста") @RequestBody @NotNull @Valid PlaylistCreateDto playlistCreateDto,
                                            @ApiParam(value = "Id юзера", example = "60") @PathVariable("userId") @NonNull Long userId) {
        if (!userService.getById(userId).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user does not exist");
        }

        String playlistName = playlistCreateDto.getName();
        Optional<Playlist> optionalPlaylist = playlistService.getPlaylistByNameAndUserId(userId, playlistName);
        if (optionalPlaylist.isPresent()) {
            return ResponseEntity.ok("The playlist already exists");
        }

        playlistCreateDto.setOwnerUserId(userId);
        Playlist newPlaylist = playlistConverter.toEntity(playlistCreateDto);
        playlistService.create(newPlaylist);
        return ResponseEntity.status(HttpStatus.CREATED).body(playlistConverter.toPlaylistGetDto(newPlaylist));
    }

    @ApiOperation(value = "Удаление плейлиста по Id для пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Плейлист удален"),
            @ApiResponse(code = 404, message = "Плейлист не найден")})
    @DeleteMapping(value = "/user/{userId}/playlists/{playlistId}")
    public ResponseEntity<?> deletePlaylist(@ApiParam(value = "Id плейлиста", example = "2") @PathVariable @NotNull Long playlistId,
                                            @ApiParam(value = "Id юзера", example = "60") @PathVariable("userId") @NonNull Long userId) {
        if (!playlistService.userHasPlaylist(userId, playlistId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No playlist with id %s for user %s", playlistId, userId));
        }
        playlistService.deleteById(playlistId);
        logger.info(String.format("Плейлист id %s удален", playlistId));
        return ResponseEntity.ok().body(String.format("Playlist with id %s deleted", playlistId));
    }

    @ApiOperation(value = "Получение списка плейлистов пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Плейлисты получены", response = PlaylistGetDto.class, responseContainer = "List")})
    @GetMapping(value = "/user/{userId}/playlists", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<?> getPlaylistsOfUser(@ApiParam(value = "Id юзера", example = "60") @PathVariable("userId") @NonNull Long userId,
                                                @ApiParam(value = "Текущая страница", example = "1") @RequestParam("currentPage") int currentPage,
                                                @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        Map <String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        logger.info(String.format("Плейлисты пользователя %s отправлены", userId));
        return ResponseEntity.ok().body(playlistDtoService.getAllByUserId(parameters));
    }

    @ApiOperation(value = "Получение плейлиста по Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Плейлист получен", response = PlaylistGetDto.class),
            @ApiResponse(code = 404, message = "Плейлист не найден")})
    @GetMapping(value = "/playlists/{playlistId}")
    public ResponseEntity<?> getPlaylistById(@ApiParam(value = "Id плейлиста", example = "2") @PathVariable @NotNull Long playlistId) {
        Optional<PlaylistGetDto> optionalPlaylistGetDto = playlistDtoService.getById(playlistId);
        if (!optionalPlaylistGetDto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No playlist with id %s", playlistId));
        }
        logger.info(String.format("Плейлист %s отправлен", playlistId));
        return ResponseEntity.ok().body(optionalPlaylistGetDto.get());
    }

    @ApiOperation(value = "Добавление аудио в плейлист")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио добавлено", response = PlaylistGetDto.class),
            @ApiResponse(code = 400, message = "Плейлист уже содержит это аудио"),
            @ApiResponse(code = 404, message = "Плейлист или аудио не найдены")})
    @PutMapping(value = "/playlists/{playlistId}/audio")
    public ResponseEntity<?> addAudioToPlaylist(@ApiParam(value = "Id плейлиста", example = "2") @PathVariable @NotNull Long playlistId,
                                                @ApiParam(value = "Id аудио", example = "11") @RequestParam("audioId") @NotNull Long audioId) {
        Optional<Playlist> playlistOptional = playlistService.getByIdWithContent(playlistId);
        if (!playlistOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No playlist with id %s", playlistId));
        }
        Optional<Audios> audiosOptional = audiosService.getByIdWithMedia(audioId);
        if (!audiosOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No audio with id %s found", audioId));
        }
        Playlist playlist = playlistOptional.get();
        if (!playlist.addAudio(audiosOptional.get())) {
            return ResponseEntity.badRequest().body(String.format("This playlist already contains audio with id %s", audioId));
        }
        playlistService.update(playlist);
        logger.info(String.format("Аудио %s добавлено в плейлист %s", audioId, playlistId));
        return ResponseEntity.ok().body(playlistConverter.toPlaylistGetDto(playlist));
    }

    @ApiOperation(value = "Удаление аудио из плейлиста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио удалено", response = PlaylistGetDto.class),
            @ApiResponse(code = 400, message = "Плейлист не содержит это аудио"),
            @ApiResponse(code = 404, message = "Плейлист или аудио не найдены")})
    @DeleteMapping(value = "/playlists/{playlistId}/audio/{audioId}")
    public ResponseEntity<?> removeAudioFromPlaylist(@ApiParam(value = "Id плейлиста", example = "2") @PathVariable @NotNull Long playlistId,
                                                     @ApiParam(value = "Id аудио", example = "10") @PathVariable @NotNull Long audioId) {
        Optional<Playlist> playlistOptional = playlistService.getByIdWithContent(playlistId);
        if (!playlistOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No playlist with id %s for current user", playlistId));
        }
        Optional<Audios> audiosOptional = audiosService.getById(audioId);
        if (!audiosOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No audio with id %s found", audioId));
        }
        Playlist playlist = playlistOptional.get();
        if (!playlist.removeAudio(audiosOptional.get())) {
            return ResponseEntity.badRequest().body(String.format("This playlist has no audio with id %s", audioId));
        }
        playlistService.update(playlist);
        logger.info(String.format("Аудио %s удалено из плейлиста %s", audioId, playlistId));
        return ResponseEntity.ok().body(playlistConverter.toPlaylistGetDto(playlist));
    }

    @ApiOperation(value = "Получение содержимого плейлиста")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио отправлено", response = AudioDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Плейлист найден")})
    @GetMapping(value = "/playlists/{playlistId}/audio", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<?> getAudioFromPlaylist(@ApiParam(value = "Id плейлиста", example = "2") @PathVariable @NotNull Long playlistId,
                                                  @ApiParam(value = "Отступ", example = "0") @RequestParam("currentPage") @NotNull int currentPage,
                                                  @ApiParam(value = "Количество данных на страницу", example = "10") @RequestParam("itemsOnPage") @NotNull int itemsOnPage) {
        if (!playlistService.existById(playlistId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No playlist with id %s", playlistId));
        }
        logger.info(String.format("Аудио из плейлиста %s отправлено", playlistId));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("playlistId", playlistId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(audioDtoService.getAudioFromPlaylist(parameters));
    }


}
