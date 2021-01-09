package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends GenericDaoAbstract<User, Long> implements UserDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return entityManager.createQuery("SELECT u from User u").getResultList();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public Optional<User> getByEmailWithRole(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u " +
                        "FROM User u " +
                        "JOIN FETCH u.role " +
                        "WHERE u.email = :email", User.class)
                .setParameter("email", email);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public boolean existByEmail(String email) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u) " +
                        "FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return (count > 0);
    }

    public boolean existsAnotherByEmail(String email, Long userId) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u) " +
                        "FROM User u " +
                        "WHERE u.email = :email " +
                        "AND NOT u.userId = :userId ", Long.class)
                .setParameter("email", email)
                .setParameter("userId", userId)
                .getSingleResult();
        return (count > 0);
    }

    @Override
    public void updateInfo(User user) {
        Query query = entityManager.createQuery(
                "UPDATE User u SET " +
                        "u.firstName = :firstName" +
                        ",u.lastName =:lastName" +
                        ",u.aboutMe = :aboutMe" +
                        ",u.avatar = :avatar" +
                        ",u.city = :city" +
                        ",u.dateOfBirth = :dateOfBirth" +
                        ",u.education = :education" +
                        ",u.email = :email" +
                        ",u.linkSite = :linkSite" +
                        ",u.profession = :profession" +
                        ",u.lastRedactionDate = :lastRedactionDate" +
                        " WHERE u.userId = :id")
                .setParameter("firstName", user.getFirstName())
                .setParameter("lastName", user.getLastName())
                .setParameter("aboutMe", user.getAboutMe())
                .setParameter("avatar", user.getAvatar())
                .setParameter("city", user.getCity())
                .setParameter("dateOfBirth", user.getDateOfBirth())
                .setParameter("education", user.getEducation())
                .setParameter("email", user.getEmail())
                .setParameter("linkSite", user.getLinkSite())
                .setParameter("profession", user.getProfession())
                .setParameter("id", user.getUserId())
                .setParameter("lastRedactionDate", user.getLastRedactionDate());
        query.executeUpdate();

    }

    @Override
    public Optional<User> getByIdWithAudios(Long id) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u " +
                        "FROM User AS u " +
                        "LEFT JOIN FETCH u.audios " +
                        "WHERE u.userId = :paramId", User.class)
                .setParameter("paramId", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public Optional<User> getByIdWithVideos(Long id) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u " +
                        "FROM User AS u " +
                        "LEFT JOIN FETCH u.videos " +
                        "WHERE u.userId = :paramId", User.class)
                .setParameter("paramId", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public void updateUserPassword(User user) {
        Query query = entityManager.createQuery(
                "UPDATE User u SET " +
                        "u.password = :password" +
                        " WHERE u.userId = :id");
        query.setParameter("password", user.getPassword());
        query.setParameter("id", user.getUserId());
        query.executeUpdate();
    }
}