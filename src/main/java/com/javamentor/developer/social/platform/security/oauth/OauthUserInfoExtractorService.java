package com.javamentor.developer.social.platform.security.oauth;

import com.javamentor.developer.social.platform.models.entity.user.Active;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.security.util.PassayRandomPasswordGenerator;
import com.javamentor.developer.social.platform.service.abstracts.model.user.ActiveService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.RoleService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class OauthUserInfoExtractorService {

    @Autowired
    private UserService userService;

    @Autowired
    private ActiveService activeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PassayRandomPasswordGenerator passwordGenerator;

    protected final Log logger = LogFactory.getLog(getClass());

    private Role role;

    private Active active;

    public void getUser( Map<String, Object> map ) {
        if(map.containsKey("sub")) {
            extractGoogleUser(map);
        } else {
            extractVkUser(map);
        }

    }


    public void extractVkUser( Map<String, Object> map ) {
        ArrayList<Object> response = (ArrayList<Object>) map.get("response");
        Map<String, Object> responseMap = (Map<String, Object>) response.get(0);

        String email = (String) responseMap.get("id");

        Optional<User> externalUser = userService.getByEmail(email);

        if(externalUser.isPresent()) {
            return;
        }

        instantiateRoleAndActive();

        String firstname = (String) responseMap.get("first_name");
        String lastName = (String) responseMap.get("last_name");
        String avatar = (String) responseMap.get("photo_max");
        String noPhoto = "https://vk.com/images/camera_200.png";
        String password = passwordGenerator.generatePassword();

        if(avatar.equals(noPhoto)) {
            avatar = null;
        }
        String education = (String) responseMap.get("university_name");

        User user = User.builder()
                .firstName(firstname)
                .lastName(lastName)
                .email(email)
                .password(password)
                .education(education)
                .avatar(avatar)
                .role(role)
                .active(active)
                .isEnable(true)
                .build();

        userService.create(user);
    }

    public void extractGoogleUser( Map<String, Object> map ) {

        String email = (String) map.get("sub");

        Optional<User> externalUser = userService.getByEmail(email);

        if(externalUser.isPresent()) {
            return;
        }

        instantiateRoleAndActive();
        String name = (String) map.get("given_name");
        String lastName = (String) map.get("family_name");
        String avatar = (String) map.get("picture");
        String password = passwordGenerator.generatePassword();

        User user = User.builder()
                .firstName(name)
                .lastName(lastName)
                .email(email)
                .password(password)
                .role(role)
                .active(active)
                .isEnable(true)
                .avatar(avatar)
                .build();

        userService.create(user);

    }

    /**
     * Если кому-то в будущем будет интересно - что это за уродливый метод.
     * Была необходимость инстанцировать зависимые сущности Role и Active.
     * Так как на этапе инициализации контекста вместо необходимых бинов
     * связанных сервисов подсовываются прокси и при попытке инстанцирования
     * в блоке инициализации падает с ошибкой обращаясь к сервису-пустышке -
     * пришлось ввести специальный метод.
     * Вызывается перед сборкой нового юзера и первым дело проверяет,
     * инстанцированно ли одно из полей (так как бин сервиса живет,
     * пока живет приложение - достаточно единожды определить поля при первом вызове), почему одно, -
     * потому что не существует возможности, когда одно поле будет определено, а другое нет.
     */
    protected void instantiateRoleAndActive() {

        if(role != null) {
            return;
        }
        Optional<Active> tempActive = activeService.getByActiveName("ACTIVE");
        Optional<Role> tempRole = roleService.getByRoleName("USER");

        if(!tempActive.isPresent()) {
            logger.fatal("The Active entity with name \"ACTIVE\" is not represented in the database \n  " +
                    " A new entity will be created to avoid errors ");
        }
        active = tempActive.orElse(Active.builder().name("ACTIVE").build());

        if(!tempRole.isPresent()) {
            logger.fatal("The Role entity with name \"USER\" is not represented in the database \n  " +
                    " A new entity will be created to avoid errors ");
        }
        role = tempRole.orElse(Role.builder().name("USER").build());
    }

}

