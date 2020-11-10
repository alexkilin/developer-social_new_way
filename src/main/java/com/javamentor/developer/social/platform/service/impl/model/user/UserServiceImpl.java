package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserDao;
import com.javamentor.developer.social.platform.models.dto.UserResetPasswordDto;
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
    public Optional<User> getByEmail(String email) {
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

        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(userResetPasswordDto.getPassword()));
            userDAO.update(user.get());
        }
    }

    @Transactional
    public void updateInfo(User user) {
        Optional<User> userOld = userDAO.getById(user.getUserId());
       userDAO.updateInfo(user);
        user.setPassword(userOld.get().getPassword());
        user.setRole(userOld.get().getRole());
        user.setActive(userOld.get().getActive());
        user.setIsEnable(userOld.get().getIsEnable());
        user.setLanguages(userOld.get().getLanguages());
        user.setPersistDate(userOld.get().getPersistDate());
        user.setLastRedactionDate(userOld.get().getLastRedactionDate());
        user.setStatus(userOld.get().getStatus());
        userDAO.update(user);
    }



    @Override
    public User getPrincipal() {
        Optional <User> optinalUser = userDAO.getById(65L);
        if(optinalUser.isPresent()){
            return optinalUser.get();
        } else {
            return null;
        }
    }

}
