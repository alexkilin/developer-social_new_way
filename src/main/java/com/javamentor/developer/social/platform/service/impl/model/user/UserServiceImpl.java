package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserDao;
import com.javamentor.developer.social.platform.models.dto.UserResetPasswordDto;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl extends GenericServiceAbstract<User, Long> implements UserService {

    private final UserDao userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDAO, PasswordEncoder passwordEncoder) {
        super(userDAO);
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public User getByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    @Override
    public boolean existByEmail(String email) {
        return userDAO.existByEmail(email);
    }

    public boolean existsAnotherByEmail(String email, Long userId) {
        return userDAO.existsAnotherByEmail(email, userId);
    }

    @Override
    public void deleteById(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return userDAO.existById(id);
    }

    @Transactional
    public void setPassword(UserResetPasswordDto userResetPasswordDto, Long userId) {
        Optional<User> user = userDAO.getById(userId);
        user.get().setPassword(passwordEncoder.encode(userResetPasswordDto.getPassword()));
        userDAO.update(user.get());
    }

    @Transactional
    public void updateInfo(User user) {
        Optional<User> userOld = userDAO.getById(user.getUserId());
        user.setPassword(userOld.get().getPassword());
        user.setRole(userOld.get().getRole());
        user.setActive(userOld.get().getActive());
        user.setIsEnable(userOld.get().getIsEnable());
        userDAO.update(user);
    }

    @Override
    public User getPrincipal() {
        return new User(60L, "User59", "LastNameUser59", new Date("2008-05-30 08:20:12.121000"),
                "MIT University", "My description about life - User59",
                  "www.myavatar59.ru/9090", "user59@user.ru", "userpass59", null,
                        LocalDateTime.of(2020, 10, 14, 23, 56, 33, 569805),
                        LocalDateTime.of(2020, 10, 14, 23, 56, 35, 897490),
                 true, "SPb", "www.mysite.ru", new Role(2L, "User", new Set<User>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<User> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(User user) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends User> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        }),
                  "free", null, null, null, null, null);
    }

}
