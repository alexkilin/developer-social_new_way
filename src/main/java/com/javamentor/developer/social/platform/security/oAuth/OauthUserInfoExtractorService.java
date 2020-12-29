package com.javamentor.developer.social.platform.security.oAuth;

import com.javamentor.developer.social.platform.models.entity.user.Active;
import com.javamentor.developer.social.platform.models.entity.user.Provider;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.ActiveService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.RoleService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    protected final Log logger = LogFactory.getLog(getClass());

    private Role role;

    private Active active;


    public void extractVkUser( Map<String, Object> map , OAuth2RestOperations restTemplate ) {

        String email = (String) restTemplate
                .getOAuth2ClientContext()
                .getAccessToken()
                .getAdditionalInformation()
                .get("email");

        Optional<User> externalUser = userService.getByEmail(email);

        if(externalUser.isPresent()) {
            return;
        }

        instantiateRoleAndActive();

        ArrayList<Object> response = (ArrayList<Object>) map.get("response");
        Map<String, Object> responseMap = (Map<String, Object>) response.get(0);
        String firstname = (String) responseMap.get("first_name");
        String lastName = (String) responseMap.get("last_name");
        String id = String.valueOf(responseMap.get("id"));
        String avatar = (String) responseMap.get("photo_max");
        String noPhoto = "https://vk.com/images/camera_200.png";

        if(avatar.equals(noPhoto)) {
            avatar = null;
        }
        String education = (String) responseMap.get("university_name");

        User user = User.builder()
                .firstName(firstname)
                .lastName(lastName)
                .email(email)
                .password("password")
                .education(education)
                .avatar(avatar)
                .role(role)
                .active(active)
                .isEnable(true)
                .externalId(id)
                .provider(Provider.VK)
                .build();

        userService.create(user);
    }

    public void extractGoogleUser( Map<String, Object> map ) {

        String email = (String) map.get("email");

        Optional<User> externalUser = userService.getByEmail(email);

        if(externalUser.isPresent()) {
            return;
        }

        instantiateRoleAndActive();

        String id = (String) map.get("sub");
        String googleName = (String) map.get("name");
        String name = (String) map.get("given_name");
        String lastName = (String) map.get("family_name");
        String avatar = (String) map.get("picture");

        User user = User.builder()
                .firstName(name)
                .lastName(lastName)
                .email(email)
                .password("password")
                .role(role)
                .active(active)
                .isEnable(true)
                .externalId(id)
                .provider(Provider.GOOGLE)
                .avatar(avatar)
                .build();

        userService.create(user);

    }

    protected void instantiateRoleAndActive() {

        if(role != null /*& active != null*/) {
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

