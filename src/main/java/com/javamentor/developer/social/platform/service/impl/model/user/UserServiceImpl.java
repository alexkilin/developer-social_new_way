package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserDao;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends GenericServiceAbstract<User, Long> implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
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
        Optional<User> userOld = userDao.getById(user.getUserId());
        userDao.updateInfo(user);
        user.setPassword(userOld.get().getPassword());
        user.setRole(userOld.get().getRole());
        user.setActive(userOld.get().getActive());
        user.setIsEnable(userOld.get().getIsEnable());
        user.setLanguages(userOld.get().getLanguages());
        user.setPersistDate(userOld.get().getPersistDate());
        user.setLastRedactionDate(userOld.get().getLastRedactionDate());
        user.setStatus(userOld.get().getStatus());
        userDao.update(user);
    }

    @Override
    @Transactional
    public User getPrincipal() {
        Optional <User> optionalUser = userDao.getById(65L);
        return optionalUser.orElse(null);
    }
}
