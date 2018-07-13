package org.art.web.labs.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.labs.dao.UserDao;
import org.art.web.labs.dao.exceptions.DAOSystemException;
import org.art.web.labs.model.chat.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User DAO implementation
 */
public class UserDaoMapImpl implements UserDao {

    public static final Logger LOG = LogManager.getLogger(UserDaoMapImpl.class);

    private final Map<Long, User> users = new ConcurrentHashMap<>();

    public UserDaoMapImpl(Map<Long, User> initialUsers) {
        this.users.putAll(initialUsers);
    }

    @Override
    public User save(User user) throws DAOSystemException {
        User savedUser = users.put(user.getId(), user);
        if (savedUser != null) {
            LOG.error("The user with such ID has already saved in the DB!");
            throw new DAOSystemException("The user with such ID has already saved in the DB!");
        }
        return user;
    }

    @Override
    public User get(Long id) throws DAOSystemException {
        User user = users.get(id);
        if (user == null) {
            LOG.error("There is no user in the database with such ID!");
            throw new DAOSystemException("There is no user in the database with such ID!");
        }
        return user;
    }

    @Override
    public User update(User user) throws DAOSystemException {
        User updUser = users.get(user.getId());
        if (updUser == null) {
            LOG.error("There is no user in the database with such ID!");
            throw new DAOSystemException("There is no user in the database with such ID!");
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(Long id) throws DAOSystemException {
        User delUser = users.remove(id);
        if (delUser == null) {
            LOG.error("There is no user in the database with such ID!");
            throw new DAOSystemException("There is no user in the database with such ID!");
        }
    }

    @Override
    public User getUserByLogin(String login) throws DAOSystemException {
        Long userId = null;
        if (login != null) {
            userId = users.keySet().stream()
                    .filter(id -> login.equals(users.get(id).getLogin()))
                    .findFirst()
                    .orElse(null);
        }
        if (userId == null) {
            LOG.error("There is no user in the database with such login!");
            throw new DAOSystemException("There is no user in the database with such login!");
        }
        return users.get(userId);
    }

    @Override
    public List<User> getAllUsers() throws DAOSystemException {
        return new ArrayList<>(users.values());
    }
}
