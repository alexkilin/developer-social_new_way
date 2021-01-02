package com.javamentor.developer.social.platform.restv2.controllers;

import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.users.UserAuthorizationDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Данный Sql-стэйтмент можно хранить в отдельном .sql файле, учитывая, что он везде одинаковый.
 * Задать фазу использования перед методом
 * executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
 * А после метода очищать данные(см. второй @Sql).
 * Это дает более чистые и независимые друг о друга тесты.
 */
@Sql(statements = {
        "Insert into active(id, name) values(1, 'test')" ,
        "Insert into role(id, name) values(1, 'USER')" ,

        "Insert into Users(user_id,first_name, last_name, email, last_redaction_date, persist_date, active_id, role_id, password) " +
                "values (1,'user666','user666', 'admin666@user.ru', '2020-08-04 16:42:03.157535', '2020-08-04 16:42:03.157535', 1, 1,'user666')" ,
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(statements = {"delete from Users" , "delete from active" , "delete from role"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class AuthenticationAndLogoutControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecurityHelper securityHelper;

    private final Gson gson = new Gson();

    private final String apiUrl = "/auth";

    protected final Log logger = LogFactory.getLog(getClass());

    @Test
    void createAuthenticationToken() throws Exception {
        UserAuthorizationDto user666 = UserAuthorizationDto.builder()
                .email("admin666@user.ru")
                .password("user666")
                .build();

        MockHttpServletResponse response = mockMvc.perform(post(apiUrl + "/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user666)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        User currentUserDetails = User.builder()
                .email("admin666@user.ru")
                .build();

        String responseJwt = response.getContentAsString();

        boolean validateToken = securityHelper
                .validateToken(responseJwt , currentUserDetails);

        assertTrue(validateToken);
/**
 * Если покажется странным использование логов.
 * Они более удобочитаемы, чем andDo(print()).
 */


        logger.info("\n\nРЕЗУЛЬТАТ КОРРЕКТНОГО ЗАПРОСА: "+
                "\nСТАТУС ЗАПРОСА: \n-> " + response.getStatus() +
                "\nПОЛУЧЕННЫЙ JWT: \n-> " + responseJwt + "\n" +
                "РЕЗУЛЬТАТ ВАЛИДАЦИИ ТОКЕНА:\n-> " + validateToken);

    }

    /**
     * Для свагера не описано поведение контроллера при предоставлении неверных данных
     */
    @Test
    void createAuthenticationTokenWrongEmail() throws Exception{
        UserAuthorizationDto invalidEmail = UserAuthorizationDto.builder()
                .email("НЕСУЩЕСТВУЮЩИЙ")
                .password("ПОЛЬЗОВАТЕЛЬ")
                .build();

        MockHttpServletResponse invalidEmailResponse = mockMvc.perform(post(apiUrl + "/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(invalidEmail)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        String content = invalidEmailResponse.getContentAsString();
        int status = invalidEmailResponse.getStatus();

        logger.info("\n\nРЕЗУЛЬТАТЫ ЗАПРОСА НА ПОЛУЧЕНИЕ ТОКЕНА С НЕПРАВИЛЬНЫМ EMAIL: " +
                "\nСТАТУС ЗАПРОСА: \n-> " + status +
                "\nПОЛУЧЕННЫЙ КОНТЕНТ: \n-> " + content);

    }
    @Test
    void createAuthenticationTokenWrongPassword() throws Exception{
        UserAuthorizationDto invalidPassword = UserAuthorizationDto.builder()
                .email("admin666@user.ru")
                .password("НЕВЕРНЫЙ ПАРОЛЬ")
                .build();

        MockHttpServletResponse invalidPasswordResponse = mockMvc.perform(post(apiUrl + "/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(invalidPassword)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        String content = invalidPasswordResponse.getContentAsString();
        int status = invalidPasswordResponse.getStatus();

        logger.info("\n\nРЕЗУЛЬТАТЫ ЗАПРОСА НА ПОЛУЧЕНИЕ ТОКЕНА С НЕПРАВИЛЬНЫМ PASSWORD: " +
                "\nСТАТУС ЗАПРОСА: \n-> " + status +
                "\nПОЛУЧЕННЫЙ КОНТЕНТ: \n-> " + content);
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
    void getPrincipal() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(get(apiUrl + "/principal"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String responseContentAsString = response.getContentAsString();

        assertTrue(responseContentAsString.contains("admin666@user.ru"));

        logger.info("\n\nРЕЗУЛЬТАТ ПОЛУЧЕНИЯ PRINCIPAL: "+
                "\nПОЛУЧЕННЫЙ КОНТЕНТ: \n-> " + responseContentAsString);
    }

    /**
     * В данный момент доступ к контроллеру осуществляется по адресу "/logout/".
     * Так как @RequestMapping у класса контроллера "/logout" - а маппинг метода - "/".
     * Адрес "/logout" - predefined - зарезервирован Спрингом и так же существует и работает.
     * Возвращает 3xx редирект на страничку "/login?logout".
     * Если нужно предоставить возможность редиректа фронту, можно обойтись добавлением в конфиг -
     * .and().logout().logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
     * Последнее, что остается в этом контроллере - это вызов старог метода securityHelper.logout(),
     * который делает - SecurityContextHolder.getContext().setAuthentication(null);
     * Мертворожденный метод-велосипед.
     * Его так же можно заменить добавив в конфиг -
     * .and().logout().clearAuthentication(true).deleteCookies("Authorization")
     * И от данного контроллера можно окончательно избавиться.
     * Если так и задумывалось и обе странички нужны - то данное описание можно удалить.
     * Хотя бы, если можно, давайте, пожалуйста, изменим метод с get на post.
     */
    @Test
    @WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
    void makeLogout() throws Exception{

        MockHttpServletResponse response = mockMvc.perform(get("/logout/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

       logger.info("\n\nПОЛУЧЕННЫЙ СТАТУС ПРИ ПОПЫТКЕ LOGOUT: \n-> " +
               response.getStatus());

    }

}
