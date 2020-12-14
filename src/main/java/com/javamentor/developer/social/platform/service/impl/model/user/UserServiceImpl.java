package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserDao;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends GenericServiceAbstract<User, Long> implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, @Lazy PasswordEncoder passwordEncoder) {
        super(userDao);
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public Optional<User> getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    @Transactional
    public boolean existByEmail(String email) {
        return userDao.existByEmail(email);
    }

    @Override
    @Transactional
    public boolean existsAnotherByEmail(String email, Long userId) {
        return userDao.existsAnotherByEmail(email, userId);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public boolean existById(Long id) {
        return userDao.existById(id);
    }

    @Override
    @Transactional
    public void updateUserPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.updateUserPassword(user);
    }

    @Override
    @Transactional
    public void updateInfo(User user) {
        user.setLastRedactionDate(LocalDateTime.now());
        userDao.updateInfo(user);
    }
}
